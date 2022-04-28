package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.OrdersDAO;
import com.ecommerce.model.Order;

@Service
public class OrdersServiceImpl implements OrdersService{

	
	@Autowired
	OrdersDAO ordersDAO;
	
	@Override
	public void addOrder(String status, String name_on_card, String card_number, String expiration_date, int userId,
			int productId) {
		ordersDAO.addOrder(status, name_on_card, card_number, expiration_date, userId, productId);
	}

	@Override
	public List<Order> getAllOrders(int userId) {
			return ordersDAO.getAllOrders(userId);
	}

}
