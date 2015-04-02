package im.gsj.dao;

import im.gsj.entity.City;
import im.gsj.hbase.HbaseConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

@Service
public class CityDao {

	/**
	 * 取出各省信息
	 */
	public List<City> getProvince() throws IOException {
		List<City> cityList = new ArrayList<City>();
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("City"));
			try {
				Scan s = new Scan();
				SingleColumnValueFilter scf = new SingleColumnValueFilter(Bytes.toBytes("ci"), Bytes.toBytes("parentId"), CompareOp.EQUAL, Bytes.toBytes(0));
				s.setFilter(scf);
				ResultScanner rs = table.getScanner(s);
				try {
					for (Result r = rs.next(); r != null; r = rs.next()) {
						City city = toCity(r);
						cityList.add(city);
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
		return cityList;
	}

	/**
	 * 将一行结果集转换成city对象
	 */
	private City toCity(Result r) {
		City city = new City();
		city.setId(Bytes.toInt(r.getRow())); // id
		city.setName(Bytes.toString(r.getValue(Bytes.toBytes("ci"), Bytes.toBytes("name")))); // name
		city.setEname(Bytes.toString(r.getValue(Bytes.toBytes("ci"), Bytes.toBytes("ename")))); // ename
		city.setParentId(Bytes.toInt(r.getValue(Bytes.toBytes("ci"), Bytes.toBytes("parentId")))); // parentId
		city.setCode(Bytes.toString(r.getValue(Bytes.toBytes("ci"), Bytes.toBytes("code")))); // code
		return city;
	}

	/**
	 * 取出一个城市信息
	 */
	public City get(Integer id) throws IOException {
		City city;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("City"));
			try {
				Get g = new Get(Bytes.toBytes(id));
				Result r = table.get(g);
				city = toCity(r);
			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
		return city;
	}

	/**
	 * 根据cityId 查出其下一层的子集
	 */
	public List<City> queryList(String property, Integer value) throws IOException {
		List<City> cityList = new ArrayList<City>();
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("City"));
			try {
				SingleColumnValueFilter scf = new SingleColumnValueFilter(Bytes.toBytes("ci"), Bytes.toBytes("parentId"), CompareOp.EQUAL,
						Bytes.toBytes(value));
				Scan s = new Scan();
				s.setFilter(scf);
				ResultScanner rs = table.getScanner(s);
				for(Result r = rs.next(); r != null; r = rs.next()){
					City city = toCity(r);
					cityList.add(city);
				}
			} finally {
				table.close();
			}
		} finally {
			connection.close();
		}
		return cityList;
	}
}
