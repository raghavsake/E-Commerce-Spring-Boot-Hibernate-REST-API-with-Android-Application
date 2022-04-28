package com.project.ecommerce.view;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.project.ecommerce.R;
import com.project.ecommerce.databinding.ActivityLoginBinding;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.utils.ProgressDialog;
import com.project.ecommerce.utils.Validation;
import com.project.ecommerce.viewmodel.LoginViewModel;
import com.project.ecommerce.model.LoginApiResponse;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        binding.buttonLogin.setOnClickListener(this);
        binding.textViewSignUp.setOnClickListener(this);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // If user logged in, go directly to ProductActivity
        if (LoginUtils.getInstance(this).isLoggedIn()) {
            goToProductActivity();
        }
    }

    private void logInUser() {
        String email = binding.inputEmail.getText().toString();
        String password = binding.inputPassword.getText().toString();

        if (email.isEmpty()) {
            binding.inputEmail.setError(getString(R.string.email_required));
            binding.inputEmail.requestFocus();
        }

        if (Validation.isValidEmail(email)) {
            binding.inputEmail.setError(getString(R.string.enter_a_valid_email_address));
            binding.inputEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            binding.inputPassword.setError(getString(R.string.password_required));
            binding.inputPassword.requestFocus();
            return;
        }

        if (!Validation.isValidPassword(password)) {
            binding.inputPassword.setError(getString(R.string.password__at_least_8_characters));
            binding.inputPassword.requestFocus();
            return;
        }

        AlertDialog alert = ProgressDialog.createAlertDialog(this);

        loginViewModel.getLoginResponseLiveData(email,password).observe(this, loginApiResponse -> {
            if (!loginApiResponse.isError()) {
                LoginUtils.getInstance(this).saveUserInfo(loginApiResponse);
                Toast.makeText(this, loginApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                alert.dismiss();
                goToProductActivity();
            }else {
                alert.dismiss();
                Toast.makeText(this, loginApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                logInUser();
                break;
            case R.id.textViewSignUp:
                goToSignUpActivity();
                break;
            default:
        }
    }

    private void goToSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void goToProductActivity() {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
