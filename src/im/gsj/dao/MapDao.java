package im.gsj.dao;

import im.gsj.entity.Map;
import im.gsj.hbase.HbaseConfig;

import java.io.IOException;
import java.util.UUID;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
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
public class MapDao {

	/**
	 * 删除指定商店的地图信息
	 */
	public void deleteByShop(String shopId) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Map"));
			try {
				Scan s = new Scan();
				s.addColumn(Bytes.toBytes("m"), Bytes.toBytes("shopId"));
				FilterList fl = new FilterList(new PageFilter(1));
				SingleColumnValueFilter scf = new SingleColumnValueFilter(Bytes.toBytes("m"), Bytes.toBytes("shopId"), CompareOp.EQUAL,
						Bytes.toBytes(shopId));
				fl.addFilter(scf);
				ResultScanner rs = table.getScanner(s);
				try {
					Result r = rs.next();
					if (r != null) {
						Delete d = new Delete(r.getRow());
						table.delete(d);
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
	}

	/**
	 * 查出某商店的地理信息
	 */
	public Map query(String property, String value) throws IOException {
		Map map = null;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Map"));
			try {
				SingleColumnValueFilter scf = new SingleColumnValueFilter(Bytes.toBytes("m"), Bytes.toBytes("shopId"), CompareOp.EQUAL,
						Bytes.toBytes(value));
				Scan s = new Scan();
				s.setFilter(scf);
				Result r = table.getScanner(s).next();
				if (r != null) {
					map = toMap(r);
				}
			} finally {
				table.close();
			}
		} finally {
			connection.close();
		}
		return map;
	}

	/**
	 * 将hbase一行记录转成map对象
	 */
	private Map toMap(Result r) {
		Map map = new Map();
		map.setId(Bytes.toString(r.getRow())); // id
		map.setShopId(Bytes.toString(r.getValue(Bytes.toBytes("m"), Bytes.toBytes("shopId")))); // shopId
		map.setLongitude(Bytes.toFloat(r.getValue(Bytes.toBytes("m"), Bytes.toBytes("longitude")))); // longitude
		map.setLatitude(Bytes.toFloat(r.getValue(Bytes.toBytes("m"), Bytes.toBytes("latitude")))); // latitude
		return map;
	}

	/**
	 * 将地图信息保存在hbase中
	 */
	public void save(Map map) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
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
	 * 设置map各值
	 */
	private Put setValue(Map map) {
		// 生成Id
		map.setId(UUID.randomUUID().toString());
		Put p = new Put(Bytes.toBytes(map.getId()));
		p = setPut(p, map);
		return p;
	}
	/**
	 * 在现有put的基础上设置map的各属性值
	 */
	private Put setPut(Put p, Map map) {
		p.add(Bytes.toBytes("m"), Bytes.toBytes("shopId"), Bytes.toBytes(map.getShopId()));
		p.add(Bytes.toBytes("m"), Bytes.toBytes("longitude"), Bytes.toBytes(map.getLongitude()));
		p.add(Bytes.toBytes("m"), Bytes.toBytes("latitude"), Bytes.toBytes(map.getLatitude()));
		return p;
	}
}
