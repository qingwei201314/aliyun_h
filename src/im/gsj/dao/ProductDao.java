package im.gsj.dao;

import im.gsj.entity.Product;
import im.gsj.hbase.HbaseConfig;
import im.gsj.util.PageHbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.NullComparator;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

@Service
public class ProductDao {

	/**
	 * 取得某一商店最近的产品(from Hbase)
	 */
	public PageHbase<Product> getNewProductHbase(String shop_id, String startRow, boolean pre) throws IOException {
		PageHbase<Product> pageHbase = new PageHbase<Product>();
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Product"));
			try {
				Scan scan = new Scan();
				scan.addFamily(Bytes.toBytes("p"));
				// name 或 description 中有包含q内容的记录
				FilterList secondFilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
				SingleColumnValueFilter shopFilter = new SingleColumnValueFilter(Bytes.toBytes("p"), Bytes.toBytes("shopId"), CompareOp.EQUAL,
						Bytes.toBytes(shop_id));
				secondFilterList.addFilter(shopFilter);

				secondFilterList.addFilter(new PageFilter(pageHbase.getOrgPageSize()));
				scan.setFilter(secondFilterList);
				if (org.apache.commons.lang.StringUtils.isNotBlank(startRow)) {
					scan.setStartRow(Bytes.toBytes(startRow));
				}
				if (pre) {
					// 如果是查上一页
					scan.setReversed(true);
					ResultScanner scanner = table.getScanner(scan);
					try {
						for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
							Product product = toProduct(rr);
							pageHbase.getList().add(0, product);
						}
					} finally {
						scanner.close();
					}
				} else {
					// 下一页
					ResultScanner scanner = table.getScanner(scan);
					try {
						for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
							Product product = toProduct(rr);
							pageHbase.getList().add(product);
						}
					} finally {
						scanner.close();
					}
				}
			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
		return pageHbase;
	}

	/**
	 * 取得某一商店某一类别的产品
	 */
	public List<Product> listProduct(String categoryId) throws IOException {
		List<Product> productList = new ArrayList<Product>();
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Product"));
			try {
				Scan s = new Scan();
				SingleColumnValueFilter scf = new SingleColumnValueFilter(Bytes.toBytes("p"), Bytes.toBytes("categoryId"), CompareOp.EQUAL,
						Bytes.toBytes(categoryId));
				s.setFilter(scf);
				ResultScanner rs = table.getScanner(s);
				try {
					for (Result r = rs.next(); r != null; rs.next()) {
						Product product = toProduct(r);
						productList.add(product);
					}
				} finally {
					rs.close();
				}
			} finally {
				table.close();
			}
		} finally {
			connection.close();
		}
		return productList;
	}

	/**
	 * 根据关键字查询某一商店的产品(fromHbase)
	 * 
	 * @throws IOException
	 */
	public PageHbase<Product> searchProductHbase(String shopId, String q, String startRow, boolean next) throws IOException {
		PageHbase<Product> pageHbase = new PageHbase<Product>();
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Product"));
			try {
				Scan scan = new Scan();
				scan.addFamily(Bytes.toBytes("p"));
				// name 或 description 中有包含q内容的记录
				FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
				SingleColumnValueFilter nameFilter = new SingleColumnValueFilter(Bytes.toBytes("p"), Bytes.toBytes("name"), CompareOp.EQUAL,
						new RegexStringComparator(q));
				filterList.addFilter(nameFilter);
				SingleColumnValueFilter descriptionFilter = new SingleColumnValueFilter(Bytes.toBytes("p"), Bytes.toBytes("description"),
						CompareOp.EQUAL, new RegexStringComparator(q));
				filterList.addFilter(descriptionFilter);

				FilterList secondFilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, filterList);
				SingleColumnValueFilter shopFilter = new SingleColumnValueFilter(Bytes.toBytes("p"), Bytes.toBytes("shopId"), CompareOp.EQUAL,
						Bytes.toBytes(shopId));
				secondFilterList.addFilter(shopFilter);
				secondFilterList.addFilter(new PageFilter(pageHbase.getPageSize() + 1));
				scan.setFilter(secondFilterList);
				if (org.apache.commons.lang.StringUtils.isNotBlank(startRow)) {
					scan.setStartRow(Bytes.toBytes(startRow));
				}
				if (next) {
					// 下一页
					ResultScanner scanner = table.getScanner(scan);
					try {
						for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
							Product product = toProduct(rr);
							pageHbase.getList().add(product);
						}
					} finally {
						scanner.close();
					}
				} else {
					// 如果是查上一页
					scan.setReversed(true);
					ResultScanner scanner = table.getScanner(scan);
					try {
						for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
							Product product = toProduct(rr);
							pageHbase.getList().add(0, product);
						}
					} finally {
						scanner.close();
					}
				}

			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
		return pageHbase;
	}

	/**
	 * 根据关键字查询某一商店的产品(hbase)
	 */
	public PageHbase<Product> searchProductHbase(String q, String startRow, boolean pre, Integer pageSize) throws IOException {
		PageHbase<Product> pageHbase = new PageHbase<Product>();
		//手机版网页需要一页显示多条记录
		if(pageSize!=null){
			pageHbase.setPageSize(pageSize);
		}
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Product"));
			try {
				Scan scan = new Scan();
				scan.addFamily(Bytes.toBytes("p"));
				FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
				
				//名字中包含查询内容
				SingleColumnValueFilter nameFilter = new SingleColumnValueFilter(Bytes.toBytes("p"), Bytes.toBytes("name"), CompareOp.EQUAL,
						new SubstringComparator(q));
				filterList.addFilter(nameFilter);
				
				//描述中包含查询内容
				SingleColumnValueFilter descriptionFilter = new SingleColumnValueFilter(Bytes.toBytes("p"), Bytes.toBytes("description"),
						CompareOp.EQUAL, new SubstringComparator(q));
				descriptionFilter.setFilterIfMissing(true);
				
				filterList.addFilter(descriptionFilter);

				FilterList secondFilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, filterList);
				secondFilterList.addFilter(new PageFilter(pageHbase.getOrgPageSize()));
				scan.setFilter(secondFilterList);

				//如果有则，则为查第一页
				if (!StringUtils.isBlank(startRow)) {
					scan.setStartRow(Bytes.toBytes(startRow));
				}

				if (pre) {
					// 如果是查上一页
					scan.setReversed(true);
					ResultScanner scanner = table.getScanner(scan);
					try {
						for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
							Product product = toProduct(rr);
							pageHbase.getList().add(0, product);
						}
					} finally {
						scanner.close();
					}
					
					//判断是否有上一页
					if(pageHbase.getList().size() >= pageHbase.getOrgPageSize()){
						pageHbase.setHasPre(true);
					}
					//判断是否有下一页
					pageHbase.setHasNext(true);
				} else {
					// 下一页
					ResultScanner scanner = table.getScanner(scan);
					try {
						for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
							Product product = toProduct(rr);
							pageHbase.getList().add(product);
						}
					} finally {
						scanner.close();
					}
					
					//判断是否有上一页
					if(!StringUtils.isBlank(startRow)){
						pageHbase.setHasPre(true);
					}
					//判断是否有下一页
					if(pageHbase.getList().size() >= pageHbase.getOrgPageSize()){
						pageHbase.setHasNext(true);
					}
				}

			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
		return pageHbase;
	}

	/**
	 * 将产品保存在hbase中
	 */
	public void saveHbase(Product product) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Product"));
			try {
				Put p = setValue(product);
				table.put(p);
			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
	}

	/**
	 * 设置product各值
	 */
	private Put setValue(Product product) {
		// 生成Id
		product.setId(UUID.randomUUID().toString());
		Put p = new Put(Bytes.toBytes(product.getId()));
		p = setPut(p, product);
		return p;
	}
	/**
	 * 在现有put的基础上设置product的各属性值
	 */
	private Put setPut(Put p, Product product) {
		p.addColumn(Bytes.toBytes("p"), Bytes.toBytes("name"), Bytes.toBytes(product.getName()));
		p.addColumn(Bytes.toBytes("p"), Bytes.toBytes("description"), Bytes.toBytes(product.getDescription()));
		p.addColumn(Bytes.toBytes("p"), Bytes.toBytes("shopId"), Bytes.toBytes(product.getShopId()));
		p.addColumn(Bytes.toBytes("p"), Bytes.toBytes("categoryId"), Bytes.toBytes(product.getCategoryId()));
		p.addColumn(Bytes.toBytes("p"), Bytes.toBytes("createTime"), Bytes.toBytes(new Date().getTime()));
		return p;
	}

	/**
	 * 取出hbase中一个产品信息,或一个图片信息
	 */
	public Product getHbase(String id) throws IOException {
		Product product;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Product"));
			try {
				Get g = new Get(Bytes.toBytes(id));
				Result r = table.get(g);
				product = toProduct(r);
			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
		return product;
	}

	/**
	 * 取出各值放入product对象中
	 */
	private Product toProduct(Result r) {
		Product product = new Product();
		product.setId(Bytes.toString(r.getRow()));
		product.setName(Bytes.toString(r.getValue(Bytes.toBytes("p"), Bytes.toBytes("name"))));
		product.setDescription(Bytes.toString(r.getValue(Bytes.toBytes("p"), Bytes.toBytes("description"))));
		product.setShopId(Bytes.toString(r.getValue(Bytes.toBytes("p"), Bytes.toBytes("shopId"))));
		product.setCategoryId(Bytes.toString(r.getValue(Bytes.toBytes("p"), Bytes.toBytes("categoryId"))));
		product.setCreateTime(new Date(Bytes.toLong(r.getValue(Bytes.toBytes("p"), Bytes.toBytes("createTime")))));
		return product;
	}

	/**
	 * 查出某一类别下的所有产品
	 */
	public List<Product> queryListHbase(String categoryId) throws IOException {
		List<Product> productList = new ArrayList<Product>();
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Product"));
			try {
				Scan scan = new Scan();
				scan.addFamily(Bytes.toBytes("p"));
				FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
				SingleColumnValueFilter categoryFilter = new SingleColumnValueFilter(Bytes.toBytes("p"), Bytes.toBytes("categoryId"),
						CompareOp.EQUAL, Bytes.toBytes(categoryId));
				filterList.addFilter(categoryFilter);

				scan.setFilter(filterList);
				ResultScanner scanner = table.getScanner(scan);
				try {
					for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
						Product product = toProduct(rr);
						productList.add(product);
					}
				} finally {
					scanner.close();
				}
			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
		return productList;
	}

	/**
	 * 删除一个对象.(可以是一个产品，也可以是一个图片)
	 */
	public void deleteHbase(String id) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Product"));
			try {
				Delete d = new Delete(Bytes.toBytes(id));
				table.delete(d);
			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
	}

	/**
	 * 更新记录.(Hbase)
	 */
	public void updateHbase(Product entity) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Product"));
			try {
				Put p = new Put(Bytes.toBytes(entity.getId()));
				p = setPut(p, entity);
				table.put(p);
			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
	}
}
