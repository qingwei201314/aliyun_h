package import2hbase.mysql2hbase;

import im.gsj.entity.Category;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 导入类别信息
 * @author: Kevin Zhang
 */
public class ImportCategory {
	public static void main(String[] args) throws SQLException, IOException {
		ImportUtil importUtil = new ImportUtil();
		java.sql.Connection conn = importUtil.connectionDB();
		try {
			PreparedStatement st = conn.prepareStatement("select * from category");
			ResultSet rs = st.executeQuery();
			try {
				while (rs.next()) {
					String id = rs.getString("id");
					String name = rs.getString("name");
					String shopId = rs.getString("shop_id");
					
					Category category = new Category();
					category.setId(id);
					category.setName(name);
					category.setShopId(shopId);

					ImportCategory importcategory = new ImportCategory();
					importcategory.save(category);
					System.out.println(id);
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
	private void save(Category category) throws IOException {
		Connection connection = ConnectionFactory.createConnection(ImportUtil.getConfig());
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
	 * 设置User各值
	 */
	private Put setValue(Category category) {
		Put p = new Put(Bytes.toBytes(category.getId()));

		if (category.getName() != null) {
			p.add(Bytes.toBytes("c"), Bytes.toBytes("name"), Bytes.toBytes(category.getName()));
		}

		if (category.getShopId() != null) {
			p.add(Bytes.toBytes("c"), Bytes.toBytes("shopId"), Bytes.toBytes(category.getShopId()));
		}
		return p;
	}
}
