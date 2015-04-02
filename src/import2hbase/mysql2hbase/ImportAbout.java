package import2hbase.mysql2hbase;

import im.gsj.entity.About;

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
 * 导入“关于”信息
 * @author: Kevin Zhang
 */
public class ImportAbout {
	public static void main(String[] args) throws SQLException, IOException {
		ImportUtil importUtil = new ImportUtil();
		java.sql.Connection conn = importUtil.connectionDB();
		try {
			PreparedStatement st = conn.prepareStatement("select * from about");
			ResultSet rs = st.executeQuery();
			try {
				while (rs.next()) {
					String id = rs.getString("id");
					String shopId = rs.getString("shop_id");
					String content = rs.getString("content");
					
					About about = new About();
					about.setId(id);
					about.setShopId(shopId);
					about.setContent(content);

					ImportAbout importabout = new ImportAbout();
					importabout.save(about);
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
	private void save(About about) throws IOException {
		Connection connection = ConnectionFactory.createConnection(ImportUtil.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("About"));
			try {
				Put p = setValue(about);
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
	private Put setValue(About about) {
		Put p = new Put(Bytes.toBytes(about.getId()));

		if (about.getShopId() != null) {
			p.add(Bytes.toBytes("a"), Bytes.toBytes("shopId"), Bytes.toBytes(about.getShopId()));
		}

		if (about.getContent() != null) {
			p.add(Bytes.toBytes("a"), Bytes.toBytes("content"), Bytes.toBytes(about.getContent()));
		}
		return p;
	}
}
