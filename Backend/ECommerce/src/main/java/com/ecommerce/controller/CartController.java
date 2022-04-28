package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.CartApiResponse;
import com.ecommerce.model.Product;
import com.ecommerce.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@GetMapping(value="/")
	public ResponseEntity<CartApiResponse> getAllProducts(@RequestParam("userId") int userId){
		List<Product> p=cartService.getAllProducts(userId);
		return ResponseEntity.status(200).body(new CartApiResponse(p));
		
	}
	
	@PostMapping(value = "/add")
	public ResponseEntity<String> add(@RequestParam("userId")  int userId,
			@RequestParam("productId")  int productId) {
		try
		{
			cartService.addProduct(userId, productId);
			return ResponseEntity.status(200).body("Your product is added to Cart");
	
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Your product is not added to Cart");
			
		}
	}
	
	@DeleteMapping(value="/remove")
	public ResponseEntity<String> removeProduct(@RequestParam("userId")  int userId,
			@RequestParam("productId")  int productId) {
		try
		{
			cartService.deleteProduct(userId, productId);
			return ResponseEntity.status(200).body("Your product is deleted from cart");
	
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Your product is not deleted from cart");
			
		}
	}
}
