package com.ecommerce.model;
public class Order {

    public Order(int id, String product_name, String order_number, String order_date, double price, String status,
			String name, String address) {
		super();
		this.id = id;
		this.product_name = product_name;
		this.order_number = order_number;
		this.order_date = order_date;
		this.price = price;
		this.status = status;
		this.name = name;
		this.address = address;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", product_name=" + product_name + ", order_number=" + order_number + ", order_date="
				+ order_date + ", price=" + price + ", status=" + status + ", name=" + name + ", address=" + address
				+ ", phone=" + phone + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	private int id;
    private String product_name;
    private String order_number;
    private String order_date;
    private double price;
    private String status;
    private String name;
    private String address;
    private String phone;

  }
