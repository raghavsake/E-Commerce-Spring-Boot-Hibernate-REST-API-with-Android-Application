package com.ecommerce.dao;

import java.util.List;

import com.ecommerce.model.Order;

public interface OrdersDAO {

	void addOrder(String status,String name_on_card, String card_number, String expiration_date, int userId, int productId);
	
	List<Order> getAllOrders(int userId);
}
