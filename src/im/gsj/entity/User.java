package im.gsj.entity;

/**
 * 用户表（create 'User', 'u'）
 * @author: Kevin Zhang
 */
public class User {
	private String id;
	private String phone;
	private String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
