package import2hbase.mysql2hbase;

import im.gsj.entity.Shop;

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
 * 导入商店信息
 * 
 * @author: Kevin Zhang
 */
public class ImportShop {
	public static void main(String[] args) throws SQLException, IOException {
		ImportShop importshop = new ImportShop();
		ImportUtil importUtil = new ImportUtil();
		java.sql.Connection conn = importUtil.connectionDB();
		try {
			PreparedStatement st = conn.prepareStatement("select * from shop");
			ResultSet rs = st.executeQuery();
			try {
				while (rs.next()) {
					String id = rs.getString("id");
					String user_id = rs.getString("user_id");
					String name = rs.getString("name");
					String short_name = rs.getString("short_name");
					String contact = rs.getString("contact");
					String address = rs.getString("address");
					int district = rs.getInt("district");
					String gate_url = rs.getString("gate_url");
					String descript = rs.getString("descript");
					
					Shop shop = new Shop();
					shop.setId(id);
					shop.setUserId(user_id);
					shop.setName(name);
					shop.setShortName(short_name);
					shop.setContact(contact);
					shop.setAddress(address);
					shop.setDistrict(district);
					shop.setGateUrl(gate_url);
					shop.setDescript(descript);
					
					System.out.println(id);
					importshop.save(shop);
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
	private void save(Shop shop) throws IOException {
		Connection connection = ConnectionFactory.createConnection(ImportUtil.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Shop"));
			try {
				Put p = setValue(shop);
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
	private Put setValue(Shop shop) {
		Put p = new Put(Bytes.toBytes(shop.getId()));
		p.add(Bytes.toBytes("s"), Bytes.toBytes("userId"), Bytes.toBytes(shop.getUserId()));
		if(shop.getName()!=null){
			p.add(Bytes.toBytes("s"), Bytes.toBytes("name"), Bytes.toBytes(shop.getName()));
		}
		if(shop.getShortName() != null){
			p.add(Bytes.toBytes("s"), Bytes.toBytes("shortName"), Bytes.toBytes(shop.getShortName()));
		}
		if(shop.getContact() != null){
			p.add(Bytes.toBytes("s"), Bytes.toBytes("contact"), Bytes.toBytes(shop.getContact()));
		}
		if(shop.getAddress() != null){
			p.add(Bytes.toBytes("s"), Bytes.toBytes("address"), Bytes.toBytes(shop.getAddress()));
		}
		if(shop.getDistrict() != null){
			p.add(Bytes.toBytes("s"), Bytes.toBytes("district"), Bytes.toBytes(shop.getDistrict()));
		}
		if(shop.getGateUrl() != null){
			p.add(Bytes.toBytes("s"), Bytes.toBytes("gateUrl"), Bytes.toBytes(shop.getGateUrl()));
		}
		if(shop.getDescript() != null){
			p.add(Bytes.toBytes("s"), Bytes.toBytes("descript"), Bytes.toBytes(shop.getDescript()));
		}
		return p;
	}
}
