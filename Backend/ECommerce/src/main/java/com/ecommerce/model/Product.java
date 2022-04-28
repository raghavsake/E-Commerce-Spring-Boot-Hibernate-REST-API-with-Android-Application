package com.ecommerce.model;
public class Product   {

    private int id;
    public Product(int id, String product_name, 
    		double price, int quantity, 
    		String supplier, String category,
			String image) {
		super();
		this.id = id;
		this.product_name = product_name;
		this.price = price;
		this.quantity = quantity;
		this.supplier = supplier;
		this.category = category;
		this.image = image;
	}
    
    public Product(String product_name, 
    		double price, int quantity, 
    		String supplier, String category,
			String image) {
		super();
		this.product_name = product_name;
		this.price = price;
		this.quantity = quantity;
		this.supplier = supplier;
		this.category = category;
		this.image = image;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", product_name=" + product_name + ", price=" + price + ", quantity=" + quantity
				+ ", supplier=" + supplier + ", category=" + category + ", image=" + image + ", isInCart=" + isInCart
				+ "]";
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getIsInCart() {
		return isInCart;
	}
	public void setIsInCart(int isInCart) {
		this.isInCart = isInCart;
	}
	private String product_name;
    private double price;
    private int quantity;
    private String supplier;
    private String category;
    private String image;
    private int isInCart;
    
}
