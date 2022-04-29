package com.project.ecommerce.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.project.ecommerce.R;
import com.project.ecommerce.databinding.ActivityAddProductBinding;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.ImageUtils;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.viewmodel.AddProductViewModel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddProductActivity";
    private static final String IMAGE = "image/*";
    private ActivityAddProductBinding binding;
    private AddProductViewModel addProductViewModel;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(com.project.ecommerce.R.string.add_product));

        addProductViewModel = ViewModelProviders.of(this).get(AddProductViewModel.class);

        binding.btnSelectImage.setOnClickListener(this);

        populateSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            addProduct(filePath);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addProduct(String pathname) {
        String nameString = binding.txtName.getText().toString().trim();
        String priceString = binding.txtPrice.getText().toString().trim();
        String quantityString = binding.txtQuantity.getText().toString().trim();
        String supplierString = binding.txtSupplier.getText().toString().trim();
        String categoryString = binding.categorySpinner.getSelectedItem().toString().toLowerCase();

        if (TextUtils.isEmpty(nameString) || TextUtils.isEmpty(priceString) ||
                TextUtils.isEmpty(quantityString) || TextUtils.isEmpty(supplierString)
                || TextUtils.isEmpty(categoryString)) {
            Toast.makeText(this, getString(R.string.required_data), Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, RequestBody> map = new HashMap<>();
        map.put("name", toRequestBody(nameString));
        map.put("price", toRequestBody(priceString));
        map.put("quantity", toRequestBody(quantityString));
        map.put("supplier", toRequestBody(supplierString));
        map.put("category", toRequestBody(categoryString));

        if (TextUtils.isEmpty(pathname)) {
            Toast.makeText(this, "No picture is chosen", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(pathname);
        RequestBody requestFile = RequestBody.create(MediaType.parse(IMAGE), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        String token = LoginUtils.getInstance(this).getUserToken();

        addProductViewModel.addProduct(token,map, photo).observe(this, responseBody -> {
            try {
                if (responseBody != null) {
                    Toast.makeText(AddProductActivity.this, responseBody.string() + "", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    private void populateSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(adapter);
    }

    private void getImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AddProductActivity.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.READ_EXTERNAL_STORAGE_CODE);
            } else {

                try {
                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType(IMAGE);

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType(IMAGE);

                    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                    addProductActivityResultLauncher.launch(chooserIntent);
                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
            }
        }
    }

    ActivityResultLauncher<Intent> addProductActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri selectedImage = data.getData();
                    binding.imageOfProduct.setImageURI(selectedImage);

                    filePath = ImageUtils.getRealPathFromURI(getApplicationContext(), selectedImage);
                    Log.d(TAG, "onActivityResult: " + filePath);
                }
            });

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSelectImage) {
            getImageFromGallery();
        }
    }
}
