package com.project.ecommerce.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.ecommerce.R;
import com.project.ecommerce.adapter.CartAdapter;
import com.project.ecommerce.databinding.ActivityCartBinding;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.InternetUtils;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.viewmodel.CartViewModel;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private CartAdapter cartAdapter;
    private List<Product> favoriteList;
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(com.project.ecommerce.R.string.cart));




        setUpRecyclerView();

        getProductsInCart();
    }

    private void setUpRecyclerView() {
        binding.productsInCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productsInCart.setHasFixedSize(true);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);

    }

    private void getProductsInCart() {
        System.out.println(LoginUtils.getInstance(this).getUserInfo().getId());
        if (InternetUtils.isNetworkConnected(this)) {

            cartViewModel.getProductsInCart(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, cartApiResponse -> {
                binding.loadingIndicator.setVisibility(View.GONE);
                if (cartApiResponse != null) {
                    favoriteList = cartApiResponse.getProductsInCart();
                    if (favoriteList.size() == 0) {
                        binding.noBookmarks.setVisibility(View.VISIBLE);
                        binding.emptyCart.setVisibility(View.VISIBLE);
                    } else {
                        binding.productsInCart.setVisibility(View.VISIBLE);
                    }
                    cartAdapter = new CartAdapter(getApplicationContext(), favoriteList, product -> {
                        Intent intent = new Intent(CartActivity.this, DetailsActivity.class);
                        intent.putExtra(Constant.PRODUCT, (product));
                        startActivity(intent);
                    }, this);
                }

                binding.loadingIndicator.setVisibility(View.GONE);
                binding.productsInCart.setAdapter(cartAdapter);
            });
        } else {
            binding.emptyCart.setVisibility(View.VISIBLE);
            binding.loadingIndicator.setVisibility(View.GONE);
            binding.emptyCart.setText(getString(R.string.no_internet_connection));
        }
    }
}
