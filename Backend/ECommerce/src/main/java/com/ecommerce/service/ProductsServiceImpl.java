package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.ProductsDAO;
import com.ecommerce.model.Product;

@Service
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	ProductsDAO productsDAO;
	
	@Override
	public List<Product> getProductsByCategory(int userId, String category) {
		return productsDAO.getProductsByCategory(userId, category);
	}

	@Override
	public List<Product> searchProduct(int userId, String keyword) {
		return productsDAO.searchProduct(userId, keyword);
	}

	@Override
	public void insertProduct(String product_ame, double price, int quantity, String supplier, String category,
			String image) {
		productsDAO.insertProduct(product_ame, price, quantity, supplier, category, image);
	}

}
