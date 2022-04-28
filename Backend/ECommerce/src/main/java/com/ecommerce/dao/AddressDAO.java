package com.ecommerce.dao;

public interface AddressDAO {
	
	void addAddress(String address,
     String city,
     String country,
     String zip,
     String phone,
     int userId,
     int productId);

}
