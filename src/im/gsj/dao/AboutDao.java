package im.gsj.dao;

import im.gsj.entity.About;
import im.gsj.hbase.HbaseConfig;

import java.io.IOException;
import java.util.UUID;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
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
public class AboutDao {
	/**
	 * 将简介保存在hbase中
	 */
	public void save(About About) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("About"));
			try {
				Put p = setValue(About);
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
	 * 更新记录.(Hbase)
	 */
	public void update(About about) throws IOException {
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("About"));
			try {
				Put p = new Put(Bytes.toBytes(about.getId()));
				p = setPut(p, about);
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
	 *  根据某个属性的值查出一条about记录
	 */
	public About query(String property, String value) throws IOException {
		About about = null;
		Connection connection = ConnectionFactory.createConnection(HbaseConfig.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("About"));
			try {
				Scan s = new Scan();
				s.addFamily(Bytes.toBytes("a"));
				SingleColumnValueFilter pf = new SingleColumnValueFilter(Bytes.toBytes("a"), Bytes.toBytes(property), CompareOp.EQUAL,
						Bytes.toBytes(value));
				FilterList fl = new FilterList(new PageFilter(1));
				fl.addFilter(pf);
				s.setFilter(fl);
				ResultScanner scanner = table.getScanner(s);
				try {
					Result rr = scanner.next();
					if (rr != null) {
						about = toAbout(rr);
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
		return about;
	}

	/**
	 * 设置about各值
	 */
	private Put setValue(About about) {
		// 生成Id
		about.setId(UUID.randomUUID().toString());
		Put p = new Put(Bytes.toBytes(about.getId()));
		p = setPut(p, about);
		return p;
	}
	/**
	 * 在现有put的基础上设置product的各属性值
	 */
	private Put setPut(Put p, About about) {
		p.addColumn(Bytes.toBytes("a"), Bytes.toBytes("shopId"), Bytes.toBytes(about.getShopId()));
		p.addColumn(Bytes.toBytes("a"), Bytes.toBytes("content"), Bytes.toBytes(about.getContent()));
		return p;
	}
	
	/**
	 * 取出各值放入about对象中
	 */
	private About toAbout(Result r) {
		About about = new About();
		about.setId(Bytes.toString(r.getRow()));
		about.setShopId(Bytes.toString(r.getValue(Bytes.toBytes("a"), Bytes.toBytes("shopId"))));
		about.setContent(Bytes.toString(r.getValue(Bytes.toBytes("a"), Bytes.toBytes("content"))));
		return about;
	}
}
