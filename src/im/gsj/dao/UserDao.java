package im.gsj.dao;

import im.gsj.entity.User;
import im.gsj.hbase.HbaseConfig;

import java.io.IOException;
import java.util.UUID;

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
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

@Service
public class UserDao {
	
	/**
	 * 取出用户信息
	 */
	public User get(String id) throws IOException {
		User user = null;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("User"));
			try {
				Get g = new Get(Bytes.toBytes(id));
				Result r = table.get(g);
				if(r != null){
					user = toUser(r);
				}
			} finally {
				table.close();
			}
		} finally {
			connection.close();
		}
		return user;
	}
	
	
	/**
	 * 根据某个属性值查出第一条符合条件的记录
	 */
    public User query(String property, String value) throws IOException{
    	User user = null;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("User"));
			try {
				Scan s = new Scan();
				s.addFamily(Bytes.toBytes("u"));
				SingleColumnValueFilter pf = new SingleColumnValueFilter(Bytes.toBytes("u"), Bytes.toBytes(property), CompareOp.EQUAL,
						Bytes.toBytes(value));
				s.setFilter(pf);
				ResultScanner scanner = table.getScanner(s);
				try {
					Result rr = scanner.next();
					if(rr != null){
						user = toUser(rr);
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
		return user;
    }
    
	/**
	 * 保存用户信息
	 */
	public void save(User user) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
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
		//生成Id
		user.setId(UUID.randomUUID().toString());
		Put p = new Put(Bytes.toBytes(user.getId()));
		p = setPut(p, user);
		return p;
	}
	
	/**
	 * 在现有put的基础上设置User的各属性值 
	 */
	private Put setPut(Put p, User user){
		p.add(Bytes.toBytes("u"), Bytes.toBytes("phone"), Bytes.toBytes(user.getPhone()));
		p.add(Bytes.toBytes("u"), Bytes.toBytes("password"), Bytes.toBytes(user.getPassword()));
		return p;
	}
	
	/**
	 * 取出各值放入User对象中
	 */
	private User toUser(Result r) {
		User user = new User();
		user.setId(Bytes.toString(r.getRow()));
		user.setPhone(Bytes.toString(r.getValue(Bytes.toBytes("u"), Bytes.toBytes("phone"))));
		user.setPassword(Bytes.toString(r.getValue(Bytes.toBytes("u"), Bytes.toBytes("password"))));
		return user;
	}
	
	/**
	 * 根据phone查出对应的userId
	 */
	public String getUserId(String phone) throws IOException{
		String userId = null;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("User"));
			try {
				Scan s = new Scan();
				SingleColumnValueFilter pf = new SingleColumnValueFilter(Bytes.toBytes("u"), Bytes.toBytes("phone"), CompareOp.EQUAL,
						Bytes.toBytes(phone));
				s.setFilter(pf);
				ResultScanner scanner = table.getScanner(s);
				try {
					Result rr = scanner.next();
					if(rr != null){
						userId = Bytes.toString(rr.getRow());
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
		return userId;
	}
	
	/**
	 * 更新记录.(Hbase)
	 */
	public void update(User entity) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("User"));
			try {
				Put p = new Put(Bytes.toBytes(entity.getId()));
				p = setPut(p, entity);
				table.put(p);
			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
	}
}
