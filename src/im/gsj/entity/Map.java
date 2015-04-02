package im.gsj.entity;

/**
 * 商店在地图上的坐标(create 'Map', 'm')
 * @author Kevin
 */
public class Map {
	private String id;
	private String shopId;
	//经度
	private Float longitude;
	//纬度
	private Float latitude;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
}
