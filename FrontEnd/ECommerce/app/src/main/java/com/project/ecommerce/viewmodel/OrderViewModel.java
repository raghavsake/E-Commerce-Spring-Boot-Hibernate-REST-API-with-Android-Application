package com.project.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.project.ecommerce.model.OrderApiResponse;
import com.project.ecommerce.repository.OrderRepository;

public class OrderViewModel extends ViewModel {

    private final OrderRepository orderRepository;

    public OrderViewModel() {
        orderRepository = new OrderRepository();
    }

    public LiveData<OrderApiResponse> getOrders(int userId) {
        return orderRepository.getOrders(userId);
    }
}

