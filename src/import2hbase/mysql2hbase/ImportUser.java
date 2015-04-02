package import2hbase.mysql2hbase;

import im.gsj.entity.User;

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

public class ImportUser {

	public static void main(String[] args) throws SQLException, IOException {
		ImportUser importUser = new ImportUser();
		ImportUtil importUtil = new ImportUtil();
		java.sql.Connection conn = importUtil.connectionDB();
		try {
			PreparedStatement st = conn.prepareStatement("select * from user");
			ResultSet rs = st.executeQuery();
			try {
				while (rs.next()) {
					String id = rs.getString("id");
					String phone = rs.getString("phone");
					String password = rs.getString("password");
					User user = new User();
					user.setId(id);
					user.setPhone(phone);
					user.setPassword(password);
					System.out.println(id + phone + password);
					importUser.save(user);
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
	private void save(User user) throws IOException {
		Connection connection = ConnectionFactory.createConnection(ImportUtil.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("User"));
			try {
				Put p = setValue(user);
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
	private Put setValue(User user) {
		Put p = new Put(Bytes.toBytes(user.getId()));
		p = setPut(p, user);
		return p;
	}

	/**
	 * 在现有put的基础上设置User的各属性值
	 */
	private Put setPut(Put p, User user) {
		p.add(Bytes.toBytes("u"), Bytes.toBytes("phone"), Bytes.toBytes(user.getPhone()));
		p.add(Bytes.toBytes("u"), Bytes.toBytes("password"), Bytes.toBytes(user.getPassword()));
		return p;
	}
}
