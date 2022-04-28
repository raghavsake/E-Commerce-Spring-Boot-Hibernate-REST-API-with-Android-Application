package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.model.Product;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	CartDAO cartDAO;

	@Override
	public List<Product> getAllProducts(int userId) {
		return cartDAO.getAllProducts(userId);
	}

	@Override
	public void addProduct(int userId, int productId) {
		cartDAO.addProduct(userId, productId);
	}

	@Override
	public void deleteProduct(int userId, int productId) {
		cartDAO.deleteProduct(userId, productId);		
	}
	
}
