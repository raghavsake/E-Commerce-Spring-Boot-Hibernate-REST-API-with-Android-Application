package com.ecommerce.dao;

import java.util.List;

import com.ecommerce.model.Product;

public interface CartDAO {

	List<Product> getAllProducts(int userId);
	
	void addProduct(int userId, int productId);
	
	void deleteProduct(int userId, int productId);
}
