package com.project.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.project.ecommerce.factory.ProductDataSource;
import com.project.ecommerce.factory.ProductDataSourceFactory;
import com.project.ecommerce.model.Product;

public class CategoryViewModel extends ViewModel {

    public LiveData<PagedList<Product>> categoryPagedList;

    public void loadProductsByCategory(String category, int userId) {
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory(category,userId);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ProductDataSource.PAGE_SIZE)
                        .build();

        categoryPagedList = (new LivePagedListBuilder<>(productDataSourceFactory, pagedListConfig)).build();
    }
}
