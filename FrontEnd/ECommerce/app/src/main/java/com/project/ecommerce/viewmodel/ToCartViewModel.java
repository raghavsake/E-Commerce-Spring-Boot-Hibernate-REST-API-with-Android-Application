package com.project.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.project.ecommerce.model.Cart;
import com.project.ecommerce.repository.ToCartRepository;
import com.project.ecommerce.utils.RequestCallback;

import okhttp3.ResponseBody;

public class ToCartViewModel extends ViewModel {

    private final ToCartRepository toCartRepository;

    public ToCartViewModel() {
        toCartRepository = new ToCartRepository();
    }

    public LiveData<ResponseBody> addToCart(Cart cart, RequestCallback callback) {
        return toCartRepository.addToCart(cart, callback);
    }
}
