package import2hbase.mysql2hbase;

import im.gsj.entity.City;

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
 * 导入城市信息
 * @author: Kevin Zhang
 */
public class ImportCity {
	public static void main(String[] args) throws SQLException, IOException {
		ImportUtil importUtil = new ImportUtil();
		java.sql.Connection conn = importUtil.connectionDB();
		try {
			PreparedStatement st = conn.prepareStatement("select * from city");
			ResultSet rs = st.executeQuery();
			try {
				while (rs.next()) {
					Integer id = rs.getInt("id");
					String name = rs.getString("name");
					String ename = rs.getString("ename");
					Integer parentId = rs.getInt("parentId");
					if(parentId == null)
						parentId = 0;
					String code = rs.getString("code");

					City city = new City();
					city.setId(id);
					city.setName(name);
					city.setEname(ename);
					city.setParentId(parentId);
					city.setCode(code);

					ImportCity importcity = new ImportCity();
					importcity.save(city);
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
	private void save(City city) throws IOException {
		Connection connection = ConnectionFactory.createConnection(ImportUtil.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("City"));
			try {
				Put p = setValue(city);
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
	private Put setValue(City city) {
		Put p = new Put(Bytes.toBytes(city.getId()));

		if (city.getName() != null) {
			p.add(Bytes.toBytes("ci"), Bytes.toBytes("name"), Bytes.toBytes(city.getName()));
		}

		if (city.getEname() != null) {
			p.add(Bytes.toBytes("ci"), Bytes.toBytes("ename"), Bytes.toBytes(city.getEname()));
		}

		if (city.getParentId()!= null) {
			p.add(Bytes.toBytes("ci"), Bytes.toBytes("parentId"), Bytes.toBytes(city.getParentId()));
		}
		
		if (city.getCode()!= null) {
			p.add(Bytes.toBytes("ci"), Bytes.toBytes("code"), Bytes.toBytes(city.getCode()));
		}
		
		return p;
	}
}
