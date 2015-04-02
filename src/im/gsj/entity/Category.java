package im.gsj.entity;


/**
 * 产品类别(create 'Category', 'c')
 * @author: Kevin Zhang
 */
public class Category {
	private String id;
	private String name;
	private String shopId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
}
