package com.project.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.project.ecommerce.model.CartApiResponse;
import com.project.ecommerce.repository.CartRepository;

public class CartViewModel extends ViewModel {

    private final CartRepository cartRepository;

    public CartViewModel() {
        cartRepository = new CartRepository();
    }

    public LiveData<CartApiResponse> getProductsInCart(int userId) {
        return cartRepository.getProductsInCart(userId);
    }
}
