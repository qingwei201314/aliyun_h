package im.gsj.dao;

import im.gsj.dao.dto.CategoryDto;
import im.gsj.entity.Category;
import im.gsj.hbase.HbaseConfig;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.IteratorUtils;
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
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

@Service
public class CategoryDao {
	/**
	 * 查出某一商店的所有类别
	 */
	public List<Category> getByShop(String shopId) throws IOException {
		List<Category> categoryList = new ArrayList<Category>();
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Category"));
			try {
				Scan scan = new Scan();
				scan.addFamily(Bytes.toBytes("c"));
				SingleColumnValueFilter shopFilter = new SingleColumnValueFilter(Bytes.toBytes("c"), Bytes.toBytes("shopId"), CompareOp.EQUAL,
						Bytes.toBytes(shopId));
				scan.setFilter(shopFilter);
				ResultScanner scanner = table.getScanner(scan);
				try {
					for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
						Category category = toCategory(rr, shopId);
						categoryList.add(category);
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
		return categoryList;
	}

	/**
	 * 查出某一商店的所有类别及各类别下的一个产品
	 */
	@SuppressWarnings("unchecked")
	public List<CategoryDto> getCategoryByShop(String shopId) throws IOException, IllegalAccessException, InvocationTargetException {
		List<Category> categoryList = getByShop(shopId);

		Map<String, CategoryDto> map = new LinkedHashMap<String, CategoryDto>();

		// 查出各类别中的一个产品
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Product"));
			try {
				FilterList totalFilter = new FilterList(Operator.MUST_PASS_ONE);
				for (Category category : categoryList) {
					SingleColumnValueFilter pf = new SingleColumnValueFilter(Bytes.toBytes("p"), Bytes.toBytes("categoryId"), CompareOp.EQUAL,
							Bytes.toBytes(category.getId()));
					FilterList fl = new FilterList(new PageFilter(1));
					fl.addFilter(pf);
					totalFilter.addFilter(fl);
					// 将各值付给新的categoryDtoList
					CategoryDto categoryDto = new CategoryDto();
					BeanUtils.copyProperties(categoryDto, category);
					map.put(category.getId(), categoryDto);
				}
				Scan s = new Scan();
				s.setFilter(totalFilter);
				s.addColumn(Bytes.toBytes("p"), Bytes.toBytes("categoryId"));
				ResultScanner rs = table.getScanner(s);
				try {
					for (Result r = rs.next(); r != null; r = rs.next()) {
						String productId = Bytes.toString(r.getRow()); // productId
						String categoryId = Bytes.toString(r.getValue(Bytes.toBytes("p"), Bytes.toBytes("categoryId"))); // categoryId
						CategoryDto temp = map.get(categoryId);
						temp.setProductId(productId);
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
		Iterator<CategoryDto> it = map.values().iterator();
		return IteratorUtils.toList(it);
	}

	/**
	 * 查出除指定类别外的某一商店的所有类别
	 */
	public List<CategoryDto> getByShopExceptCurrent(String shopId, String categoryId) throws IllegalAccessException, InvocationTargetException, IOException {
		List<CategoryDto> categoryDtoList = getCategoryByShop(shopId);
		// 去掉指定的类别
		for (CategoryDto categoryDto : categoryDtoList) {
			if (categoryId.equals(categoryDto.getId())) {
				categoryDtoList.remove(categoryDto);
				break;
			}
		}
		return categoryDtoList;
	}

	/**
	 * 查出指定商店的类别数
	 */
	public Long getCategoryCount(String shopId) throws IOException {
		Long count = 0L;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Category"));
			try {
				Scan scan = new Scan();
				scan.addColumn(Bytes.toBytes("c"), Bytes.toBytes("shopId"));
				SingleColumnValueFilter shopFilter = new SingleColumnValueFilter(Bytes.toBytes("c"), Bytes.toBytes("shopId"), CompareOp.EQUAL,
						Bytes.toBytes(shopId));
				scan.setFilter(shopFilter);
				ResultScanner scanner = table.getScanner(scan);
				try {
					for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
						count ++;
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
		return count;
	}

	/**
	 * 将一行hbase记录转换成category对象
	 */
	private Category toCategory(Result r, String shopId) {
		Category category = new Category();
		category.setId(Bytes.toString(r.getRow())); // id
		category.setName(Bytes.toString(r.getValue(Bytes.toBytes("c"), Bytes.toBytes("name")))); // name
		category.setShopId(shopId);
		return category;
	}
	
	/**
	 * 将产品保存在hbase中
	 */
	public void save(Category category) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Category"));
			try {
				Put p = setValue(category);
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
	private Put setValue(Category category) {
		// 生成Id
		category.setId(UUID.randomUUID().toString());
		Put p = new Put(Bytes.toBytes(category.getId()));
		p = setPut(p, category);
		return p;
	}
	
	/**
	 * 在现有put的基础上设置product的各属性值
	 */
	private Put setPut(Put p, Category category) {
		p.add(Bytes.toBytes("c"), Bytes.toBytes("name"), Bytes.toBytes(category.getName()));
		p.add(Bytes.toBytes("c"), Bytes.toBytes("shopId"), Bytes.toBytes(category.getShopId()));
		return p;
	}
	
	/**
	 * 更新记录.(Hbase)
	 */
	public void update(Category category) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Category"));
			try {
				Put p = new Put(Bytes.toBytes(category.getId()));
				p = setPut(p, category);
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
	 * 取出category对象
	 */
	public Category get(String id, String shopId) throws IOException {
		Category category = null;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Category"));
			try {
				Get g = new Get(Bytes.toBytes(id));
				Result r = table.get(g);
				if(r != null){
					category = toCategory(r, shopId);
				}
			} finally {
				table.close();
			}
		} finally {
			connection.close();
		}
		return category;
	}
	
	/**
	 * 删除一个类别
	 */
	public void delete(String id) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Category"));
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
}
