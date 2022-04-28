package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.AddressDAO;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressDAO addressDAO;

	@Override
	public void addAddress(String address, String city, String country, String zip, String phone, int userId,
			int productId) {
		addressDAO.addAddress(address, city, country, zip, phone, userId, productId);
	}
	
	
}
