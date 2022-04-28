package com.project.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.project.ecommerce.model.Ordering;
import com.project.ecommerce.repository.OrderingRepository;

import okhttp3.ResponseBody;

public class OrderingViewModel extends ViewModel {

    private final OrderingRepository orderingRepository;

    public OrderingViewModel(  ) {
        orderingRepository = new OrderingRepository();
    }

    public LiveData<ResponseBody> orderProduct(Ordering ordering) {
        return orderingRepository.orderProduct(ordering);
    }
}
