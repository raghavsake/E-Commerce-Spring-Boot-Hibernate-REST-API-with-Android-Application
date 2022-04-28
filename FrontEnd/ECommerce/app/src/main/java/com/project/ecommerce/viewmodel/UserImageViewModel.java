package com.project.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.project.ecommerce.model.Image;
import com.project.ecommerce.repository.UserImageRepository;

public class UserImageViewModel extends ViewModel {

    private final UserImageRepository userImageRepository;

    public UserImageViewModel() {
        userImageRepository = new UserImageRepository();
    }

    public LiveData<Image> getUserImage(int userId) {
        return userImageRepository.getUserImage(userId);
    }
}
