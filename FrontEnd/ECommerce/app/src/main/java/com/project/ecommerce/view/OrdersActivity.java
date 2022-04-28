package com.project.ecommerce.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.ecommerce.R;
import com.project.ecommerce.adapter.OrderAdapter;
import com.project.ecommerce.databinding.ActivityOrdersBinding;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.viewmodel.OrderViewModel;


public class OrdersActivity extends AppCompatActivity implements OrderAdapter.OrderAdapterOnClickHandler {

    private ActivityOrdersBinding binding;
    private OrderViewModel orderViewModel;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        binding = DataBindingUtil.setContentView(this, com.project.ecommerce.R.layout.activity_orders);

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        setUpRecycleView();

        getOrders();
    }

    private void setUpRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.orderList.setLayoutManager(layoutManager);
        binding.orderList.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.orderList.addItemDecoration(dividerItemDecoration);
    }

    private void getOrders() {
        orderViewModel.getOrders(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, orderApiResponse -> {
            orderAdapter = new OrderAdapter( orderApiResponse.getOrderList(),this);
            binding.orderList.setAdapter(orderAdapter);
        });
    }

    @Override
    public void onClick(Order order) {
        Intent intent = new Intent(OrdersActivity.this, StatusActivity.class);
        // Pass an object of order class
        intent.putExtra(Constant.ORDER, (order));
        startActivity(intent);
    }
}
