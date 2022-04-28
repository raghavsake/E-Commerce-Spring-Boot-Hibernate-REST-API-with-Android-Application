package com.project.ecommerce.net;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.project.ecommerce.model.Product;

public class ProductDataSourceFactory extends DataSource.Factory{

    private final MutableLiveData<PageKeyedDataSource<Integer, Product>> productLiveDataSource = new MutableLiveData<>();

    public static ProductDataSource productDataSource;

    private final String category;
    private final int userId;

    public ProductDataSourceFactory(String category, int userId){
        this.category = category;
        this.userId = userId;
    }

    @Override
    public DataSource<Integer, Product> create() {
        productDataSource = new ProductDataSource(category, userId);

        productLiveDataSource.postValue(productDataSource);

        return productDataSource;
    }
}