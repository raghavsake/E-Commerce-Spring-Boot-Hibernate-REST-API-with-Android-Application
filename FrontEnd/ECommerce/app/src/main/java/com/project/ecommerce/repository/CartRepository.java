package com.project.ecommerce.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.project.ecommerce.model.CartApiResponse;
import com.project.ecommerce.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {

    private static final String TAG = CartRepository.class.getSimpleName();

    public LiveData<CartApiResponse> getProductsInCart(int userId) {
        final MutableLiveData<CartApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getProductsInCart(userId).enqueue(new Callback<CartApiResponse>() {
            @Override
            public void onResponse(Call<CartApiResponse> call, Response<CartApiResponse> response) {
                Log.d(TAG, "onResponse: Succeeded");

                CartApiResponse cartApiResponse = response.body();
               // System.out.println(cartApiResponse.getProductsInCart().get(0).getProductName());
                if (response.body() != null) {
                    mutableLiveData.setValue(cartApiResponse);
                    Log.d(TAG, String.valueOf(response.body().getProductsInCart()));
                }
            }

            @Override
            public void onFailure(Call<CartApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
