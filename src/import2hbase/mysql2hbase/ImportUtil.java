package import2hbase.mysql2hbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
/**
 * 导入数据公共类
 * 
 * @author: Kevin Zhang
 */
public class ImportUtil {
	public Connection connectionDB() {
		java.sql.Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Configuration getConfig() {
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "115.28.139.46");
		return config;
	}
	
	public static void main(String[] args) throws ParseException {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String newDate = sdf.format(d);
		System.out.println(newDate);
		Date d1 = sdf.parse(newDate);
		System.out.println(d1.toString());
	}
}
