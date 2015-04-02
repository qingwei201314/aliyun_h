package import2hbase.mysql2hbase;

import im.gsj.entity.Product;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 导入产品信息
 * @author: Kevin Zhang
 */
public class ImportProduct {
	public static void main(String[] args) throws SQLException, IOException {
		ImportProduct importproduct = new ImportProduct();
		ImportUtil importUtil = new ImportUtil();
		java.sql.Connection conn = importUtil.connectionDB();
		try {
			PreparedStatement st = conn.prepareStatement("select * from product");
			ResultSet rs = st.executeQuery();
			try {
				while (rs.next()) {
					String id = rs.getString("id");
					String name = rs.getString("name");
					String description = rs.getString("description");
					String shopId = rs.getString("shop_id");
					String categoryId = rs.getString("category_id");
					Date createTime = rs.getDate("create_time");

					Product product = new Product();
					product.setId(id);
					product.setName(name);
					product.setDescription(description);
					product.setShopId(shopId);
					product.setCategoryId(categoryId);
					product.setCreateTime(createTime);

					System.out.println(id);
					importproduct.save(product);
				}
			} finally {
				rs.close();
				st.close();
			}
		} finally {
			conn.close();
		}

		System.out.println("导入成功");
	}

	/**
	 * 保存
	 */
	private void save(Product product) throws IOException {
		Connection connection = ConnectionFactory.createConnection(ImportUtil.getConfig());
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
	 * 设置User各值
	 */
	private Put setValue(Product product) {
		Put p = new Put(Bytes.toBytes(product.getId()));

		if (product.getName() != null) {
			p.add(Bytes.toBytes("p"), Bytes.toBytes("name"), Bytes.toBytes(product.getName()));
		}

		if (product.getDescription() != null) {
			p.add(Bytes.toBytes("p"), Bytes.toBytes("description"), Bytes.toBytes(product.getDescription()));
		}

		if (product.getShopId() != null) {
			p.add(Bytes.toBytes("p"), Bytes.toBytes("shopId"), Bytes.toBytes(product.getShopId()));
		}

		if (product.getCategoryId() != null) {
			p.add(Bytes.toBytes("p"), Bytes.toBytes("categoryId"), Bytes.toBytes(product.getCategoryId()));
		}

		if (product.getCreateTime() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String newDate = sdf.format(product.getCreateTime());
			p.add(Bytes.toBytes("p"), Bytes.toBytes("createTime"), Bytes.toBytes(newDate));
		}

		return p;
	}
}
