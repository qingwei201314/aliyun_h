package im.gsj.dao;

import im.gsj.entity.Shop;
import im.gsj.hbase.HbaseConfig;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;

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
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

@Service
public class ShopDao {
	@Resource
	private UserDao userDao;

	/**
	 * 根据电话号码找出对应商店 
	 */
	public Shop getByPhone(String phone) throws IOException {
		String userId = userDao.getUserId(phone);
		Shop shop = query("userId", userId);
		return shop;
	}

	/**
	 * 根据某个属性值查出第一条符合条件的记录
	 */
    public Shop query(String property, String value) throws IOException{
    	Shop shop = null;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Shop"));
			try {
				Scan s = new Scan();
				s.addFamily(Bytes.toBytes("s"));
				SingleColumnValueFilter pf = new SingleColumnValueFilter(Bytes.toBytes("s"), Bytes.toBytes(property), CompareOp.EQUAL,
						Bytes.toBytes(value));
				s.setFilter(pf);
				ResultScanner scanner = table.getScanner(s);
				try {
					Result rr = scanner.next();
					if(rr != null){
						shop = toShop(rr);
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
		return shop;
    }
    
	/**
	 * 取出商店对象
	 */
	public Shop get(String id) throws IOException {
		Shop shop = null;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Shop"));
			try {
				Get g = new Get(Bytes.toBytes(id));
				Result r = table.get(g);
				if(r != null){
					shop = toShop(r);
				}
			} finally {
				table.close();
			}
		} finally {
			connection.close();
		}
		return shop;
	}
	
	/**
	 * 取出各值放入Shop对象中
	 */
	private Shop toShop(Result r) {
		Shop shop = new Shop();
		shop.setId(Bytes.toString(r.getRow()));
		shop.setUserId(Bytes.toString(r.getValue(Bytes.toBytes("s"), Bytes.toBytes("userId")))); // 用户id
		shop.setName(Bytes.toString(r.getValue(Bytes.toBytes("s"), Bytes.toBytes("name"))));
		shop.setShortName(Bytes.toString(r.getValue(Bytes.toBytes("s"), Bytes.toBytes("shortName"))));
		shop.setContact(Bytes.toString(r.getValue(Bytes.toBytes("s"), Bytes.toBytes("contact"))));
		shop.setAddress(Bytes.toString(r.getValue(Bytes.toBytes("s"), Bytes.toBytes("address"))));
		shop.setDistrict(Bytes.toInt(r.getValue(Bytes.toBytes("s"), Bytes.toBytes("district"))));
		shop.setGateUrl(Bytes.toString(r.getValue(Bytes.toBytes("s"), Bytes.toBytes("gateUrl"))));
		shop.setDescript(Bytes.toString(r.getValue(Bytes.toBytes("s"), Bytes.toBytes("descript"))));
		return shop;
	}
	
	/**
	 * 将商店信息保存在hbase中
	 */
	public void saveOrUpdate(Shop shop) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
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
	 * 设置shop各值
	 */
	private Put setValue(Shop shop) {
		// 生成Id
		if(shop.getId() == null){
			shop.setId(UUID.randomUUID().toString());
		}
		Put p = new Put(Bytes.toBytes(shop.getId()));
		p = setPut(p, shop);
		return p;
	}
	
	/**
	 * 在现有put的基础上设置shop的各属性值
	 */
	private Put setPut(Put p, Shop shop) {
		p.addColumn(Bytes.toBytes("s"), Bytes.toBytes("userId"), Bytes.toBytes(shop.getUserId()));
		p.addColumn(Bytes.toBytes("s"), Bytes.toBytes("name"), Bytes.toBytes(shop.getName()));
		p.addColumn(Bytes.toBytes("s"), Bytes.toBytes("shortName"), Bytes.toBytes(shop.getShortName()));
		p.addColumn(Bytes.toBytes("s"), Bytes.toBytes("contact"), Bytes.toBytes(shop.getContact()));
		p.addColumn(Bytes.toBytes("s"), Bytes.toBytes("address"), Bytes.toBytes(shop.getAddress()));
		p.addColumn(Bytes.toBytes("s"), Bytes.toBytes("district"), Bytes.toBytes(shop.getDistrict()));
		if(shop.getGateUrl() != null){
			p.addColumn(Bytes.toBytes("s"), Bytes.toBytes("gateUrl"), Bytes.toBytes(shop.getGateUrl()));
		}
		if(shop.getDescript() != null){
			p.addColumn(Bytes.toBytes("s"), Bytes.toBytes("descript"), Bytes.toBytes(shop.getDescript()));
		}
		return p;
	}
}
