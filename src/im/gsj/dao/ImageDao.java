package im.gsj.dao;

import im.gsj.entity.Image;
import im.gsj.hbase.HbaseConfig;
import im.gsj.util.PageHbase;
import im.gsj.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

@Service
public class ImageDao {

	/**
	 * 根据产品Id找出产品图片(from Hbase)
	 */
	public List<Image> getImageListByProductIdHbase(String productId) throws IOException {
		List<Image> imageList = new ArrayList<Image>();
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Image"));
			try {
				Scan scan = new Scan();
				scan.addFamily(Bytes.toBytes("i"));
				FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
				SingleColumnValueFilter productFilter = new SingleColumnValueFilter(Bytes.toBytes("i"), Bytes.toBytes("productId"), CompareOp.EQUAL,
						Bytes.toBytes(productId));
				filterList.addFilter(productFilter);

				scan.setFilter(filterList);
				ResultScanner scanner = table.getScanner(scan);
				try {
					for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
						Image image = toImage(rr);
						imageList.add(image);
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
		return imageList;
	}

	/**
	 * 查出某一类别的一页图片
	 */
	public List<Image> pageByCategoryHbase(String categoryId, String startRow, boolean pre) throws IOException {
		List<Image> listHbase = new ArrayList<Image>();
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Image"));
			try {
				Scan scan = new Scan();
				scan.addFamily(Bytes.toBytes("i"));
				FilterList secondFilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
				SingleColumnValueFilter categoryFilter = new SingleColumnValueFilter(Bytes.toBytes("i"), Bytes.toBytes("categoryId"),
						CompareOp.EQUAL, Bytes.toBytes(categoryId));
				secondFilterList.addFilter(categoryFilter);

				secondFilterList.addFilter(new PageFilter(Util.pageSize));
				scan.setFilter(secondFilterList);
				if (StringUtils.isNotBlank(startRow)) {
					scan.setStartRow(Bytes.toBytes(startRow));
				}
				if (pre) {
					// 如果是查上一页
					scan.setReversed(true);
					ResultScanner scanner = table.getScanner(scan);
					try {
						for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
							Image image = toImage(rr);
							listHbase.add(0, image);
						}
					} finally {
						scanner.close();
					}
				} else {
					// 下一页
					ResultScanner scanner = table.getScanner(scan);
					try {
						for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
							Image image = toImage(rr);
							listHbase.add(image);
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
		return listHbase;
	}

	/**
	 * 取出某一类别的一页图片记录
	 */
	public PageHbase<Image> pageHbase(String categoryId, String startRow, boolean pre) throws IOException {
		PageHbase<Image> page = new PageHbase<Image>();
		List<Image> imageList = pageByCategoryHbase(categoryId, startRow, pre);
		page.setList(imageList);
		return page;
	}

	/**
	 * 取出某一产品的一页图片信息
	 * 
	 * @throws IOException
	 */
	public PageHbase<Image> pageImageHbase(String productId, String startRow, boolean pre) throws IOException {
		PageHbase<Image> pageHbase = new PageHbase<Image>();
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Image"));
			try {
				Scan scan = new Scan();
				scan.addFamily(Bytes.toBytes("i"));
				// name 或 description 中有包含q内容的记录
				FilterList secondFilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
				SingleColumnValueFilter productFilter = new SingleColumnValueFilter(Bytes.toBytes("i"), Bytes.toBytes("productId"), CompareOp.EQUAL,
						Bytes.toBytes(productId));
				secondFilterList.addFilter(productFilter);

				secondFilterList.addFilter(new PageFilter(pageHbase.getOrgPageSize()));
				scan.setFilter(secondFilterList);
				if (StringUtils.isNotBlank(startRow)) {
					scan.setStartRow(Bytes.toBytes(startRow));
				}
				if (pre) {
					// 如果是查上一页
					scan.setReversed(true);
					ResultScanner scanner = table.getScanner(scan);
					try {
						for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
							Image image = toImage(rr);
							pageHbase.getList().add(0, image);
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
							Image image = toImage(rr);
							pageHbase.getList().add(image);
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
	 * 保存图片信息到hbase
	 */
	public void saveHbase(String categoryId, String productId, Image image) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Image"));
			try {
				Put p = setValue(categoryId, productId, image);
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
	 * 设置Image各值
	 */
	private Put setValue(String categoryId, String productId, Image image) {
		image.setId(UUID.randomUUID().toString());
		Put p = new Put(Bytes.toBytes(image.getId()));
		p.add(Bytes.toBytes("i"), Bytes.toBytes("categoryId"), Bytes.toBytes(categoryId));
		p.add(Bytes.toBytes("i"), Bytes.toBytes("productId"), Bytes.toBytes(productId));
		p.add(Bytes.toBytes("i"), Bytes.toBytes("path"), Bytes.toBytes(image.getPath()));
		p.add(Bytes.toBytes("i"), Bytes.toBytes("postfix"), Bytes.toBytes(image.getPostfix()));
		return p;
	}

	/**
	 * 取出某一产品的第一张图片
	 */
	public Image getFirstImage(String productId) throws IOException {
		Image image = null;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Image"));
			try {
				Scan s = new Scan();
				SingleColumnValueFilter productFilter = new SingleColumnValueFilter(Bytes.toBytes("i"), Bytes.toBytes("productId"), CompareOp.EQUAL,
						Bytes.toBytes(productId));
				s.setFilter(productFilter);
				ResultScanner scanner = table.getScanner(s);
				try {
					Result rr = scanner.next();
					if (rr != null) {
						image = toImage(rr);
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
		return image;
	}

	/**
	 * 取出各值放入image对象中
	 */
	private Image toImage(Result r) {
		Image image = new Image();
		image.setId(Bytes.toString(r.getRow()));
		image.setPath(Bytes.toString(r.getValue(Bytes.toBytes("i"), Bytes.toBytes("path"))));
		image.setPostfix(Bytes.toString(r.getValue(Bytes.toBytes("i"), Bytes.toBytes("postfix"))));
		image.setProductId(Bytes.toString(r.getValue(Bytes.toBytes("i"), Bytes.toBytes("productId"))));
		return image;
	}

	/**
	 * 取出hbase中一个一个图片信息
	 */
	public Image getHbase(String id) throws IOException {
		Image image;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Image"));
			try {
				Get g = new Get(Bytes.toBytes(id));
				Result r = table.get(g);
				image = toImage(r);
			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
		return image;
	}
}
