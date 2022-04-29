package com.project.ecommerce.factory;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.project.ecommerce.model.Product;


public class LaptopDataSourceFactory extends DataSource.Factory{

    private final MutableLiveData<PageKeyedDataSource<Integer, Product>> laptopLiveDataSource = new MutableLiveData<>();

    public static ProductDataSource laptopDataSource;

    private final String category;
    private final int userId;

    public LaptopDataSourceFactory(String category, int userId){
        this.category = category;
        this.userId = userId;
    }

    @Override
    public DataSource<Integer, Product> create() {
        laptopDataSource = new ProductDataSource(category,userId);

        laptopLiveDataSource.postValue(laptopDataSource);

        return laptopDataSource;
    }
}