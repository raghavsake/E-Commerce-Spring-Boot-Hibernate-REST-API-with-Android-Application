package com.project.ecommerce.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.project.ecommerce.model.Ordering;
import com.project.ecommerce.net.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderingRepository {

    private static final String TAG = OrderingRepository.class.getSimpleName();

    public LiveData<ResponseBody> orderProduct(Ordering ordering) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().orderProduct("shipped",ordering.getNameOnCard(),ordering.getCardNumber(),ordering.getFullDate(),ordering.getUserId(),ordering.getProductId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: " + response.body());

                ResponseBody responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mutableLiveData;
    }


}
