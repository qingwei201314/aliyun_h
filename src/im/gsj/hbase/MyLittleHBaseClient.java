package im.gsj.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class MyLittleHBaseClient {
	public static void main(String[] args) throws IOException {
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "192.168.1.9");
		Connection connection = ConnectionFactory.createConnection(config);
		try {
			Table table = connection.getTable(TableName.valueOf("product"));
			try {
				//ae458598-724a-447f-bd05-0deae9beb0d4
				
				Put p = new Put(Bytes.toBytes("fa3d9d2e-a13f-4eff-a2d7-8a0f4d6ac3a9"));
				p.add(Bytes.toBytes("pt"), Bytes.toBytes("productId"), Bytes.toBytes("ae458598-724a-447f-bd05-0deae9beb0d4"));
				table.put(p);

				// Get g = new
				// Get(Bytes.toBytes("8a100d6543cc99640143cca7c8950006"));
				// Result r = table.get(g);
				// byte[] value = r.getValue(Bytes.toBytes("pt"),
				// Bytes.toBytes("name"));
				//
				// String valueStr = Bytes.toString(value);
				// System.out.println("GET: " + valueStr);

				// Scan s = new Scan();
				// s.setFilter(new PageFilter(1));
				// ResultScanner scanner = table.getScanner(s);
				//
				// try {
				// for (Result rr = scanner.next(); rr != null; rr =
				// scanner.next()) {
				// System.out.println("Found row: " +
				// Bytes.toString(rr.getRow()));
				// }
				// } finally {
				// scanner.close();
				// }
			} finally {
				if (table != null)
					table.close();
			}
		} finally {
			connection.close();
		}
	}

	public static void query() throws IOException {
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "192.168.1.9");
		Connection connection = ConnectionFactory.createConnection(config);
		try {
			Table table = connection.getTable(TableName.valueOf("test"));
			try {
				Scan s = new Scan();
				ResultScanner scanner = table.getScanner(s);
				try {
					for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
						System.out.println("Found row: " + rr);
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
	}
}
