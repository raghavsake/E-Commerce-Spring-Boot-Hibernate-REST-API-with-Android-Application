package com.ecommerce.service;

import java.util.List;

import com.ecommerce.model.Product;

public interface CartService {
	
	List<Product> getAllProducts(int userId);
	
	void addProduct(int userId, int productId);
	
	void deleteProduct(int userId, int productId);

}
