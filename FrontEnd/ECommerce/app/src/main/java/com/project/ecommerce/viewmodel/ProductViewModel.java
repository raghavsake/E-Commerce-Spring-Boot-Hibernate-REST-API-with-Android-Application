package com.project.ecommerce.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.project.ecommerce.factory.LaptopDataSourceFactory;
import com.project.ecommerce.factory.ProductDataSource;
import com.project.ecommerce.factory.ProductDataSourceFactory;
import com.project.ecommerce.model.Product;

public class ProductViewModel extends ViewModel {

    public LiveData<PagedList<Product>> productPagedList;

    public LiveData<PagedList<Product>> laptopPagedList;

    private static final PagedList.Config  pagedListConfig =
            (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setPageSize(ProductDataSource.PAGE_SIZE)
                    .build();

    public void loadMobiles(String category, int userId){
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory(category,userId);

        productPagedList = (new LivePagedListBuilder<>(productDataSourceFactory, pagedListConfig)).build();
    }

    public void loadLaptops(String category, int userId){
        LaptopDataSourceFactory laptopDataSourceFactory = new LaptopDataSourceFactory(category,userId);
        laptopPagedList = (new LivePagedListBuilder<>(laptopDataSourceFactory, pagedListConfig)).build();
    }

    public void invalidate(){
        if(ProductDataSourceFactory.productDataSource != null) ProductDataSourceFactory.productDataSource.invalidate();
        if(LaptopDataSourceFactory.laptopDataSource!= null) LaptopDataSourceFactory.laptopDataSource.invalidate();
    }
}
