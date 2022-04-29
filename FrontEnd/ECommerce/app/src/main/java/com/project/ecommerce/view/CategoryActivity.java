package com.project.ecommerce.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.project.ecommerce.R;
import com.project.ecommerce.adapter.ProductAdapter;
import com.project.ecommerce.databinding.ActivityCategoryBinding;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.reciever.NetworkChangeReceiver;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.InternetUtils;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.utils.OnNetworkListener;
import com.project.ecommerce.viewmodel.CategoryViewModel;

public class CategoryActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler, OnNetworkListener {

    private ActivityCategoryBinding binding;
    private ProductAdapter productAdapter;
    private CategoryViewModel categoryViewModel;
    private Snackbar snack;
    private NetworkChangeReceiver mNetworkReceiver;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, com.project.ecommerce.R.layout.activity_category);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        actionBar.setDisplayHomeAsUpEnabled(true);

        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);

        Intent intent = getIntent();
        String category = intent.getStringExtra(Constant.CATEGORY);

        getSupportActionBar().setTitle(category);

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.loadProductsByCategory(category.toLowerCase(), userID);

        setupRecyclerViews();

        getProductsByCategory();

        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);
    }

    private void setupRecyclerViews() {
        binding.categoryList.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        binding.categoryList.setHasFixedSize(true);
        productAdapter = new ProductAdapter(this,this);
    }


    public void getProductsByCategory() {
        if (InternetUtils.isNetworkConnected(this)) {
            categoryViewModel.categoryPagedList.observe(this, products -> {
                productAdapter.submitList(products);
            });

            binding.categoryList.setAdapter(productAdapter);
        }
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(CategoryActivity.this, DetailsActivity.class);
        intent.putExtra(Constant.PRODUCT, (product));
        startActivity(intent);
    }

    @Override
    public void onNetworkConnected() {
        hideSnackBar();
        getProductsByCategory();
    }

    @Override
    public void onNetworkDisconnected() {
        showSnackBar();
    }

    public void showSnackBar() {
        snack.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snack.show();
    }

    public void hideSnackBar() {
        snack.dismiss();
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkBroadcastForNougat();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mNetworkReceiver);
    }
}
