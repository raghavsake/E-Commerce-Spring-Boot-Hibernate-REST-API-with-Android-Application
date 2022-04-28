package com.project.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.project.ecommerce.model.ProductApiResponse;
import com.project.ecommerce.repository.SearchRepository;

public class SearchViewModel extends ViewModel {

    private final SearchRepository searchRepository;

    public SearchViewModel(  ) {
        searchRepository = new SearchRepository();
    }


    public LiveData<ProductApiResponse> getProductsBySearch(String keyword, int userId) {
        return searchRepository.getResponseDataBySearch(keyword, userId);
    }
}
