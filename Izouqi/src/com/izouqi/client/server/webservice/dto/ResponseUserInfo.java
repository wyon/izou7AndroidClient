package com.izouqi.client.server.webservice.dto;

//login response data
public class ResponseUserInfo extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7031576421466676203L;

	private int id;
	private String realName;
	private String phone;
	private String email;
	private String qq;
	private String headPicture;
	private String birthday; // "2014-01-11"
	private int city;
	private boolean sex; // false(0)Ϊ�У�true(1)ΪŮ
	private String introduction;
	private String careerInfo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getHeadPicture() {
		return headPicture;
	}

	public void setHeadPicture(String headPicture) {
		this.headPicture = headPicture;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getCareerInfo() {
		return careerInfo;
	}

	public void setCareerInfo(String careerInfo) {
		this.careerInfo = careerInfo;
	}
}