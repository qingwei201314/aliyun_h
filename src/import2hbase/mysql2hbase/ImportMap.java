package import2hbase.mysql2hbase;

import im.gsj.entity.Map;

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
 * 导入地图信息
 * @author: Kevin Zhang
 */
public class ImportMap {
	public static void main(String[] args) throws SQLException, IOException {
		ImportUtil importUtil = new ImportUtil();
		java.sql.Connection conn = importUtil.connectionDB();
		try {
			PreparedStatement st = conn.prepareStatement("select * from map");
			ResultSet rs = st.executeQuery();
			try {
				while (rs.next()) {
					String id = rs.getString("id");
					String shopId = rs.getString("shop_id");
					Float longitude = rs.getFloat("longitude");
					Float latitude = rs.getFloat("latitude");

					Map map = new Map();
					map.setId(id);
					map.setShopId(shopId);
					map.setLongitude(longitude);
					map.setLatitude(latitude);

					ImportMap importmap = new ImportMap();
					importmap.save(map);
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
	private void save(Map map) throws IOException {
		Connection connection = ConnectionFactory.createConnection(ImportUtil.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Map"));
			try {
				Put p = setValue(map);
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
	private Put setValue(Map map) {
		Put p = new Put(Bytes.toBytes(map.getId()));

		if (map.getShopId() != null) {
			p.add(Bytes.toBytes("m"), Bytes.toBytes("shopId"), Bytes.toBytes(map.getShopId()));
		}

		if (map.getLongitude() != null) {
			p.add(Bytes.toBytes("m"), Bytes.toBytes("longitude"), Bytes.toBytes(map.getLongitude()));
		}

		if (map.getLatitude() != null) {
			p.add(Bytes.toBytes("m"), Bytes.toBytes("latitude"), Bytes.toBytes(map.getLatitude()));
		}
		return p;
	}
}
