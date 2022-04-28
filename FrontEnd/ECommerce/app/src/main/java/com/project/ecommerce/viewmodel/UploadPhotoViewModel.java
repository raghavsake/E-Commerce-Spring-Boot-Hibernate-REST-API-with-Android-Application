package com.project.ecommerce.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.project.ecommerce.repository.UploadPhotoRepository;

import okhttp3.ResponseBody;

public class UploadPhotoViewModel extends AndroidViewModel {

    private final UploadPhotoRepository uploadPhotoRepository;

    public UploadPhotoViewModel(@NonNull Application application) {
        super(application);
        uploadPhotoRepository = new UploadPhotoRepository(application);
    }

    public LiveData<ResponseBody> uploadPhoto(String pathname) {
        return uploadPhotoRepository.uploadPhoto(pathname);
    }
}
