package com.project.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.project.ecommerce.model.LoginApiResponse;
import com.project.ecommerce.repository.LoginRepository;


public class LoginViewModel extends ViewModel {

    private final LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = new LoginRepository();
    }

    public LiveData<LoginApiResponse> getLoginResponseLiveData(String email, String password) {
        return loginRepository.getLoginResponseData(email, password);
    }
}
