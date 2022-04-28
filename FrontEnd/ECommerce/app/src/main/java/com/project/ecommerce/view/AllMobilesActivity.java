package com.project.ecommerce.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.project.ecommerce.R;
import com.project.ecommerce.adapter.ProductAdapter;
import com.project.ecommerce.databinding.ActivityAllMobilesBinding;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.viewmodel.ProductViewModel;

public class AllMobilesActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler{

    private ActivityAllMobilesBinding binding;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, com.project.ecommerce.R.layout.activity_all_mobiles);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.all_mobiles));

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadMobiles("mobile", userID);

        setupRecyclerViews();

        getAllMobiles();
    }

    private void setupRecyclerViews() {
        // Mobiles
        binding.allMobilesRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        binding.allMobilesRecyclerView.setHasFixedSize(true);
        productAdapter = new ProductAdapter(this,this);
    }

    public void getAllMobiles() {
        // Observe the productPagedList from ViewModel
        productViewModel.productPagedList.observe(this, products -> {
            productAdapter.submitList(products);
        });

        binding.allMobilesRecyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(AllMobilesActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(Constant.PRODUCT, (product));
        startActivity(intent);
    }
}
