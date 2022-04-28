package com.project.ecommerce.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import com.project.ecommerce.R;
import com.project.ecommerce.databinding.ActivityShippingAddressBinding;
import com.project.ecommerce.model.Shipping;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.viewmodel.ShippingViewModel;

import java.io.IOException;

public class ShippingAddressActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityShippingAddressBinding binding;

    private ShippingViewModel shippingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, com.project.ecommerce.R.layout.activity_shipping_address);

        shippingViewModel = ViewModelProviders.of(this).get(ShippingViewModel.class);

        binding.proceed.setOnClickListener(this);

        binding.txtName.setText(LoginUtils.getInstance(this).getUserInfo().getName());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            addShippingAddress();
        }
    }

    private void addShippingAddress() {
        String address = binding.address.getText().toString().trim();
        String city = binding.city.getText().toString().trim();
        String country = binding.country.getText().toString().trim();
        String zip = binding.zip.getText().toString().trim();
        String phone = binding.phone.getText().toString().trim();
        int userId = LoginUtils.getInstance(this).getUserInfo().getId();
        Intent intent = getIntent();
        int productId = intent.getIntExtra(Constant.PRODUCTID, 0);

        Shipping shipping = new Shipping(address, city, country, zip, phone,userId, productId);

        shippingViewModel.addShippingAddress(shipping).observe(this, responseBody -> {
            Toast.makeText(ShippingAddressActivity.this, responseBody+"", Toast.LENGTH_SHORT).show();
            Intent orderProductIntent = new Intent(ShippingAddressActivity.this, OrderProductActivity.class);
            orderProductIntent.putExtra(Constant.PRODUCTID,productId);
            startActivity(orderProductIntent);
        });
    }
}
