package com.project.ecommerce.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.project.ecommerce.R;
import com.project.ecommerce.databinding.ActivityDetailsBinding;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.utils.RequestCallback;
import com.project.ecommerce.viewmodel.ToCartViewModel;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DetailsActivity";

    private ActivityDetailsBinding binding;
    private ToCartViewModel toCartViewModel;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, com.project.ecommerce.R.layout.activity_details);

        toCartViewModel = ViewModelProviders.of(this).get(ToCartViewModel.class);

        binding.addToCart.setOnClickListener(this);
        binding.buy.setOnClickListener(this);

        getProductDetails();

    }



    private void getProductDetails() {
        // Receive the product object
        product = getIntent().getParcelableExtra(Constant.PRODUCT);

        Log.d(TAG, " isInCart " + product.isInCart());

        // Set Custom ActionBar Layout
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_title_layout);
        ((TextView) findViewById(R.id.action_bar_title)).setText(product.getProductName());

        binding.nameOfProduct.setText(product.getProductName());
        binding.priceOfProduct.setText(String.valueOf(product.getProductPrice()));

        String imageUrl =  product.getProductImage().replace("localhost:8080","").replaceAll("\\\\", "/");
//        Glide.with(this)
//                .load(imageUrl)
//                .into(binding.imageOfProduct);

        Picasso.with(this)
                .load(Constant.LOCALHOST+imageUrl)
                .into(binding.imageOfProduct);

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.addToCart){
            insertToCart(() -> product.setIsInCart(true));
            Intent cartIntent = new Intent(DetailsActivity.this, CartActivity.class);
            startActivity(cartIntent);
        }else if(view.getId() == R.id.buy){
            Intent shippingIntent = new Intent(DetailsActivity.this, ShippingAddressActivity.class);
            shippingIntent.putExtra(Constant.PRODUCTID, product.getProductId());
            startActivity(shippingIntent);
        }
    }

    private void insertToCart(RequestCallback callback) {
        Cart cart = new Cart(LoginUtils.getInstance(this).getUserInfo().getId(), product.getProductId());
        toCartViewModel.addToCart(cart, callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
