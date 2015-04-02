package im.gsj.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;

public class CreateSchema {
	 public static void createOrOverwrite(HBaseAdmin admin, HTableDescriptor table) throws IOException {
		    if (admin.tableExists(table.getName())) {
		      admin.disableTable(table.getName());
		      admin.deleteTable(table.getName());
		    }
		    admin.createTable(table);
		  }

		  public static void createSchemaTables (Configuration config) {
		    try {
		      final HBaseAdmin admin = new HBaseAdmin(config);
		      HTableDescriptor table = new HTableDescriptor(TableName.valueOf("KevinTable"));
		      table.addFamily(new HColumnDescriptor("dd").setCompressionType(Algorithm.SNAPPY));

		      System.out.print("Creating table. ");
		      createOrOverwrite(admin, table);
		      System.out.println(" Done.");

		      admin.close();
		    } catch (Exception e) {
		      e.printStackTrace();
		      System.exit(-1);
		    }
		  }
}
