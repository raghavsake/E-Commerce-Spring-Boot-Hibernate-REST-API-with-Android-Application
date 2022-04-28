package com.project.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.project.ecommerce.repository.FromCartRepository;
import com.project.ecommerce.utils.RequestCallback;

import okhttp3.ResponseBody;

public class FromCartViewModel extends ViewModel {

    private final FromCartRepository fromCartRepository;

    public FromCartViewModel(  ) {
        fromCartRepository = new FromCartRepository();
    }

    public LiveData<ResponseBody> removeFromCart(int userId, int productId, RequestCallback callback) {
        return fromCartRepository.removeFromCart(userId, productId, callback);
    }
}
