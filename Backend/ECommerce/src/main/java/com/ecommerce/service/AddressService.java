package com.ecommerce.service;

public interface AddressService {
	void addAddress(String address,
		     String city,
		     String country,
		     String zip,
		     String phone,
		     int userId,
		     int productId);

}
