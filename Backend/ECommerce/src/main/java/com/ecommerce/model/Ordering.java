package com.ecommerce.model;

public class Ordering {

    private String name_on_card;
    private String card_number;
    public Ordering(String name_on_card, String card_number, String expiration_date, int userId, int productId) {
		super();
		this.name_on_card = name_on_card;
		this.card_number = card_number;
		this.expiration_date = expiration_date;
		this.userId = userId;
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "Ordering [name_on_card=" + name_on_card + ", card_number=" + card_number + ", expiration_date="
				+ expiration_date + ", userId=" + userId + ", productId=" + productId + "]";
	}
	public String getName_on_card() {
		return name_on_card;
	}
	public void setName_on_card(String name_on_card) {
		this.name_on_card = name_on_card;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getExpiration_date() {
		return expiration_date;
	}
	public void setExpiration_date(String expiration_date) {
		this.expiration_date = expiration_date;
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
	private String expiration_date;
    private int userId;
    private int productId;

 
}



