package com.project.ecommerce.view;



import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;


import com.project.ecommerce.R;
import com.project.ecommerce.adapter.SearchAdapter;
import com.project.ecommerce.databinding.ActivityResultBinding;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.InternetUtils;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.viewmodel.SearchViewModel;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private ActivityResultBinding binding;
    private SearchAdapter searchAdapter;
    private List<Product> searchedList;
    private SearchViewModel searchViewModel;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, com.project.ecommerce.R.layout.activity_result);

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        Intent intent = getIntent();
        String keyword = intent.getStringExtra(Constant.KEYWORD);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(keyword);

        userId = LoginUtils.getInstance(this).getUserInfo().getId();

        if (InternetUtils.isNetworkConnected(getApplicationContext())) {
            search(keyword);
        }
    }

    private void search(String query) {

        binding.listOfSearchedList.setHasFixedSize(true);
        binding.listOfSearchedList.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));

        searchViewModel.getProductsBySearch(query, userId).observe(this, productApiResponse -> {
            if (productApiResponse != null) {
                searchedList = productApiResponse.getProducts();
                if (searchedList.isEmpty()) {
                    Toast.makeText(ResultActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                }

                searchAdapter = new SearchAdapter(getApplicationContext(), searchedList, product -> {
                    Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
                    // Pass an object of product class
                    intent.putExtra(Constant.PRODUCT, product);
                    startActivity(intent);
                },this);
            }
            binding.listOfSearchedList.setAdapter(searchAdapter);
        });
    }
}
