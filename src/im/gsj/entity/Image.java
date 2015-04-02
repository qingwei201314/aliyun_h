package im.gsj.entity;

/**
 * 产品的图片信息(create 'Image', 'i')
 * 
 * @author: Kevin Zhang
 */
public class Image {
	private String id;
	private String categoryId;
	private String productId;
	private String path;
	private String postfix;

	public Image() {
	}

	public Image(String productId, String path) {
		this.productId = productId;
		this.path = path;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getPostfix() {
		return postfix;
	}
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
