package com.project.ecommerce.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.project.ecommerce.R;
import com.project.ecommerce.databinding.ActivityOrderProductBinding;
import com.project.ecommerce.model.Ordering;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.viewmodel.OrderingViewModel;

import java.io.IOException;

public class OrderProductActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityOrderProductBinding binding;
    private OrderingViewModel orderingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, com.project.ecommerce.R.layout.activity_order_product);

        orderingViewModel = ViewModelProviders.of(this).get(OrderingViewModel.class);

        binding.addCard.setOnClickListener(this);

        populateSpinner();
    }

    private void orderProduct() {
        String nameOnCard = binding.nameOnCard.getText().toString().trim();
        String cardNumber = binding.cardNumber.getText().toString().trim();

        String year = binding.spinnerYearMenu.getEditableText().toString().toLowerCase();
        String month = binding.spinnerMonthMenu.getEditableText().toString().toLowerCase();
        String fullDate = year + "-" + month + "-01";

        int userId = LoginUtils.getInstance(this).getUserInfo().getId();
        Intent intent = getIntent();
        int productId = intent.getIntExtra(Constant.PRODUCTID, 0);

        Ordering ordering = new Ordering(nameOnCard,cardNumber,fullDate,userId,productId);

        orderingViewModel.orderProduct(ordering).observe(this, responseBody -> {
            Toast.makeText(OrderProductActivity.this, responseBody + "", Toast.LENGTH_SHORT).show();
            finish();
            Intent homeIntent = new Intent(OrderProductActivity.this, ProductActivity.class);
            startActivity(homeIntent);
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addCard) {
            orderProduct();
        }
    }

    private void populateSpinner() {
        String[] years = {"2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030"};
        ArrayAdapter<CharSequence> yearsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, years );
        binding.spinnerYearMenu.setAdapter(yearsAdapter);

        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        ArrayAdapter<CharSequence> monthsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, months );
        binding.spinnerMonthMenu.setAdapter(monthsAdapter);
    }
}