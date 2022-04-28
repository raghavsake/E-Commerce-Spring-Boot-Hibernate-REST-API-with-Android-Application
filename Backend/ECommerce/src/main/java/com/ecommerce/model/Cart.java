package com.ecommerce.model;
public class Cart {

    private int userId;
    private int productId;
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
	@Override
	public String toString() {
		return "Cart [userId=" + userId + ", productId=" + productId + "]";
	}
	public Cart(int userId, int productId) {
		super();
		this.userId = userId;
		this.productId = productId;
	}

    
}
