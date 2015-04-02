package import2hbase.mysql2hbase;

import im.gsj.entity.Image;

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
 * 导入图片信息
 * @author: Kevin Zhang
 */
public class ImportImage {
	public static void main(String[] args) throws SQLException, IOException {
		ImportUtil importUtil = new ImportUtil();
		java.sql.Connection conn = importUtil.connectionDB();
		try {
			PreparedStatement st = conn.prepareStatement("select i.*, p.category_id from image i left JOIN product p on i.product_id = p.id ");
			ResultSet rs = st.executeQuery();
			try {
				while (rs.next()) {
					String id = rs.getString("id");
					String productId = rs.getString("product_id");
					String path = rs.getString("path");
					String postfix = rs.getString("postfix");
					String categoryId = rs.getString("category_id");
					
					Image image = new Image();
					image.setId(id);
					image.setProductId(productId);
					image.setPath(path);
					image.setPostfix(postfix);
					image.setCategoryId(categoryId);
					
					ImportImage importimage = new ImportImage();
					importimage.save(image);
					System.out.println(id);
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
	private void save(Image image) throws IOException {
		Connection connection = ConnectionFactory.createConnection(ImportUtil.getConfig());
		try {
			Table table = connection.getTable(TableName.valueOf("Image"));
			try {
				Put p = setValue(image);
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
	private Put setValue(Image image) {
		Put p = new Put(Bytes.toBytes(image.getId()));

		if (image.getCategoryId() != null) {
			p.add(Bytes.toBytes("i"), Bytes.toBytes("categoryId"), Bytes.toBytes(image.getCategoryId()));
		}
		
		if (image.getProductId() != null) {
			p.add(Bytes.toBytes("i"), Bytes.toBytes("productId"), Bytes.toBytes(image.getProductId()));
		}

		if (image.getPath() != null) {
			p.add(Bytes.toBytes("i"), Bytes.toBytes("path"), Bytes.toBytes(image.getPath()));
		}

		if (image.getPostfix() != null) {
			p.add(Bytes.toBytes("i"), Bytes.toBytes("postfix"), Bytes.toBytes(image.getPostfix()));
		}
		return p;
	}
}
