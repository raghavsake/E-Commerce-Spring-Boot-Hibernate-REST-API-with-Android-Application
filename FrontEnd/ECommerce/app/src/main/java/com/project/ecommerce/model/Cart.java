package com.project.ecommerce.model;

import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("userId")
    private final int userId;
    @SerializedName("productId")
    private final int productId;

    public Cart(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }
}
