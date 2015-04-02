package im.gsj.entity;

/**
 * 商店简介(create 'About', 'a')
 * @author: Kevin Zhang
 */
public class About {
	private String id;
	private String shopId;
	private String content;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
}
