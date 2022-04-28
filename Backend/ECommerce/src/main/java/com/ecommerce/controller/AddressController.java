package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	AddressService addressService;
	
	@PostMapping(value = "/add")
	public ResponseEntity<String> add(@RequestParam("address") String address,
			@RequestParam("city") String city,
			@RequestParam("country")  String country,
			@RequestParam("zip")  String zip,
			@RequestParam("phone")  String phone,
			@RequestParam("userId")  int userId,
			@RequestParam("productId")  int productId) {
		try
		{
			addressService.addAddress(address, city, country, zip, phone, userId, productId);
			return ResponseEntity.status(200).body("Your address is added");
	
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Your address is not added");
			
		}
	}

}
