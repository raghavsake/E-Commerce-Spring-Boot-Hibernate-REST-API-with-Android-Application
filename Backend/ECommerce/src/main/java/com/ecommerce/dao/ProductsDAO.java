package com.ecommerce.dao;

import java.util.List;

import com.ecommerce.model.Product;

public interface ProductsDAO {
	
	List<Product> getProductsByCategory(int userId, String category);
	
	List<Product> searchProduct(int userId, String keyword);
	
	void insertProduct( String product_ame, double price, int quantity, String supplier, String category,String image);
	


}
