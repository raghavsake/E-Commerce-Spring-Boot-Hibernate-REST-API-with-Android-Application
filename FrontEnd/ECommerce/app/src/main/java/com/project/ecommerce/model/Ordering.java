package com.project.ecommerce.model;

import com.google.gson.annotations.SerializedName;

public class Ordering {

    @SerializedName("name_on_card")
    private String nameOnCard;
    @SerializedName("card_number")
    private String cardNumber;
    @SerializedName("expiration_date")
    private String fullDate;
    @SerializedName("userId")
    private int userId;
    @SerializedName("productId")
    private int productId;

    public Ordering(String nameOnCard, String cardNumber, String fullDate, int userId, int productId) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.fullDate = fullDate;
        this.userId = userId;
        this.productId = productId;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
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
}



