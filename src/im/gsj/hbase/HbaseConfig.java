package im.gsj.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

/**
 * hbase 配置文件
 */
public class HbaseConfig {
	private static Configuration config;
	public HbaseConfig(){
	}

	public static Configuration getConfig() {
		return config;
	}

	public void setHbase(String hbase) {
		if(config==null){
			config = HBaseConfiguration.create();
			config.set("hbase.zookeeper.quorum", hbase);
		}
	}
}
