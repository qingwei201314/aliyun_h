package im.gsj.entity;

/**
 * 商店实体(create 'Shop', 's')
 */
public class Shop {
	private String id;
	private String userId;
	private String name;
	private String shortName;
	private String contact;
	private String address;
	private Integer district;
	private String gateUrl;
	private String descript;

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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGateUrl() {
		return gateUrl;
	}

	public void setGateUrl(String gateUrl) {
		this.gateUrl = gateUrl;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
