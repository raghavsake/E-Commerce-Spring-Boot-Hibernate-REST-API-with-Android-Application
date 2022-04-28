package com.project.ecommerce.model;

import com.google.gson.annotations.SerializedName;

public class Shipping {

    @SerializedName("address")
    private final String address;
    @SerializedName("city")
    private final String city;
    @SerializedName("country")
    private final String country;
    @SerializedName("zip")
    private final String zip;
    @SerializedName("phone")
    private final String phone;
    @SerializedName("userId")
    private final int userId;
    @SerializedName("productId")
    private final int productId;

    public Shipping(String address, String city, String country, String zip, String phone, int userId, int productId) {
        this.address = address;
        this.city = city;
        this.country = country;
        this.zip = zip;
        this.phone = phone;
        this.userId = userId;
        this.productId = productId;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }
}
