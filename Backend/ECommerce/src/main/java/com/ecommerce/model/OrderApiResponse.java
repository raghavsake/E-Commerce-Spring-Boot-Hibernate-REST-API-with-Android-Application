package com.ecommerce.model;

import java.util.List;

public class OrderApiResponse {


    private List<Order> orders;

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "OrderApiResponse [orders=" + orders + "]";
	}

	public OrderApiResponse(List<Order> orders) {
		super();
		this.orders = orders;
	}

}
