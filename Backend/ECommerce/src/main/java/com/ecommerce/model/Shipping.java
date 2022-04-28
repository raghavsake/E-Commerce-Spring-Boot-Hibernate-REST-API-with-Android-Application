package com.ecommerce.model;
public class Shipping {

    @Override
	public String toString() {
		return "Shipping [address=" + address + ", city=" + city + ", country=" + country + ", zip=" + zip + ", phone="
				+ phone + ", userId=" + userId + ", productId=" + productId + "]";
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	private String address;
    private String city;
    private String country;
    private String zip;
    private String phone;
    private int userId;
    private int productId;

    public Shipping(String address, String city, String country, String zip, String phone, int userId, int productId) {
        this.address = address;
        this.city = city;
        this.country = country;
        this.zip = zip;
        this.phone = phone;
        this.userId = userId;
        this.productId = productId;
    }
}
