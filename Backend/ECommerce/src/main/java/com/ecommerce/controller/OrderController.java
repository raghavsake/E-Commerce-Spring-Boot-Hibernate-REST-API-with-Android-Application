package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderApiResponse;
import com.ecommerce.service.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	OrdersService ordersService;
	
	@GetMapping(value="/get")
	public ResponseEntity<OrderApiResponse> getAllProducts(@RequestParam("userId") int userId){
		List<Order> p=ordersService.getAllOrders(userId);
		return ResponseEntity.status(200).body(new OrderApiResponse(p));
		
	}
	
	@PostMapping(value = "/add")
	public ResponseEntity<String> add(@RequestParam("status")String status,@RequestParam("name_on_card")String name_on_card, 
			@RequestParam("card_number")String card_number, @RequestParam("expiration_date")String expiration_date, 
			@RequestParam("userId")int userId, @RequestParam("productId")int productId) {
		try
		{
			ordersService.addOrder(status, name_on_card, card_number, expiration_date, userId, productId);
			return ResponseEntity.status(200).body("Your order is created");
	
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Your order could not be created");
			
		}
	}

}
