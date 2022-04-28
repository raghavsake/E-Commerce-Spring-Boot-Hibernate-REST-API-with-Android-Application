package com.project.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.project.ecommerce.model.RegisterApiResponse;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.RegisterRepository;


public class RegisterViewModel extends ViewModel {

    private final RegisterRepository registerRepository;

    public RegisterViewModel() {
        registerRepository = new RegisterRepository();
    }

    public LiveData<RegisterApiResponse> getRegisterResponseLiveData(User user) {
        return registerRepository.getRegisterResponseData(user);
    }
}
