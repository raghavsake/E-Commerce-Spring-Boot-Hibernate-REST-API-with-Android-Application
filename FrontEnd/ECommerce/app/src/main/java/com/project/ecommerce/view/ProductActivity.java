package com.project.ecommerce.view;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.project.ecommerce.R;
import com.project.ecommerce.adapter.ProductAdapter;
import com.project.ecommerce.databinding.ActivityProductBinding;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.reciever.NetworkChangeReceiver;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.ImageUtils;
import com.project.ecommerce.utils.InternetUtils;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.utils.OnNetworkListener;
import com.project.ecommerce.utils.Slide;
import com.project.ecommerce.viewmodel.ProductViewModel;
import com.project.ecommerce.viewmodel.UploadPhotoViewModel;
import com.project.ecommerce.viewmodel.UserImageViewModel;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkListener, ProductAdapter.ProductAdapterOnClickHandler,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ProductActivity";
    private ActivityProductBinding binding;

    private ProductAdapter mobileAdapter;
    private ProductAdapter laptopAdapter;

    private ProductViewModel productViewModel;
    private UploadPhotoViewModel uploadPhotoViewModel;
    private UserImageViewModel userImageViewModel;

    private Snackbar snack;

    private CircleImageView circleImageView;

    private NetworkChangeReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadMobiles("mobile", userID);
        productViewModel.loadLaptops("laptop",userID);
        uploadPhotoViewModel = ViewModelProviders.of(this).get(UploadPhotoViewModel.class);
        userImageViewModel = ViewModelProviders.of(this).get(UserImageViewModel.class);

        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);

        binding.included.content.txtSeeAllMobiles.setOnClickListener(this);
        binding.included.content.txtSeeAllLaptops.setOnClickListener(this);
        binding.included.txtSearch.setOnClickListener(this);

        setUpViews();

        getMobiles();
        getLaptops();
        getUserImage();

        flipImages(Slide.getSlides());

        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);
    }

    private void setUpViews() {
        Toolbar toolbar = binding.included.toolbar;

        DrawerLayout drawer = binding.drawerLayout;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        View headerContainer = binding.navView.getHeaderView(0);
        circleImageView = headerContainer.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(this);
        TextView userName = headerContainer.findViewById(R.id.nameOfUser);
        userName.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        TextView userEmail = headerContainer.findViewById(R.id.emailOfUser);
        userEmail.setText(LoginUtils.getInstance(this).getUserInfo().getEmail());

        binding.included.content.listOfMobiles.setHasFixedSize(true);
        binding.included.content.listOfMobiles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfMobiles.setItemAnimator(null);

        binding.included.content.listOfLaptops.setHasFixedSize(true);
        binding.included.content.listOfLaptops.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfLaptops.setItemAnimator(null);


        mobileAdapter = new ProductAdapter(this, this);
        laptopAdapter = new ProductAdapter(this, this);


    }

    private void getMobiles() {
        if (InternetUtils.isNetworkConnected(this)) {
            productViewModel.productPagedList.observe(this, products -> mobileAdapter.submitList(products));

            binding.included.content.listOfMobiles.setAdapter(mobileAdapter);
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }

    private void getLaptops() {
        if (InternetUtils.isNetworkConnected(this)) {
            productViewModel.laptopPagedList.observe(this, products -> laptopAdapter.submitList(products));

            binding.included.content.listOfLaptops.setAdapter(laptopAdapter);
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }



    private void flipImages(List<Integer> images) {
        for (int image : images) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);
            binding.included.content.imageSlider.addView(imageView);
        }

        binding.included.content.imageSlider.setFlipInterval(2000);
        binding.included.content.imageSlider.setAutoStart(true);

        binding.included.content.imageSlider.setInAnimation(this, R.anim.slide_in_right);
        binding.included.content.imageSlider.setOutAnimation(this, R.anim.slide_out_left);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSeeAllMobiles:
                Intent mobileIntent = new Intent(this, AllMobilesActivity.class);
                startActivity(mobileIntent);
                break;
            case R.id.txtSeeAllLaptops:
                Intent laptopIntent = new Intent(this, AllLaptopsActivity.class);
                startActivity(laptopIntent);
                break;
            case R.id.profile_image:
                showCustomAlertDialog();
                break;
            case R.id.txtSearch:
                Intent searchIntent = new Intent(ProductActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            default:
        }
    }

    private void showNormalAlertDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null).show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.darkGreen));
    }

    private void showCustomAlertDialog() {
        final Dialog dialog = new Dialog(ProductActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_image_dialog);

        Button takePicture = dialog.findViewById(R.id.takePicture);
        Button useGallery = dialog.findViewById(R.id.useGallery);

        takePicture.setEnabled(true);
        useGallery.setEnabled(true);

        takePicture.setOnClickListener(v -> {
            launchCamera();
            dialog.cancel();
        });

        useGallery.setOnClickListener(v -> {
            getImageFromGallery();
            dialog.cancel();
        });

        dialog.show();
    }

    private void getImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.READ_EXTERNAL_STORAGE_CODE);
            } else {
                try {
                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType("image/*");

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                    getImageFromGallery.launch(chooserIntent);
                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
            }
        }
    }

    private void launchCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getImageFromCamera.launch(cameraIntent);
            }
        }
    }

    ActivityResultLauncher<Intent> getImageFromGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri imageUri = data.getData();
                    circleImageView.setImageURI(imageUri);

                    String filePath = ImageUtils.getRealPathFromURI(this, imageUri);
                    Log.d(TAG, "getImageFromGallery: " + filePath);

                    uploadPhoto(filePath);
                }
            });

    ActivityResultLauncher<Intent> getImageFromCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    circleImageView.setImageBitmap(photo);

                    Uri imageUri = ImageUtils.getImageUri(this, photo);
                    String filePath = ImageUtils.getRealPathFromURI(this, imageUri);
                    Log.d(TAG, "getImageFromCamera: " + filePath);

                    uploadPhoto(filePath);
                }
            });


    private void uploadPhoto(String pathname) {
        uploadPhotoViewModel.uploadPhoto(pathname).observe(this, responseBody -> {
            Log.d(TAG, "Image Uploaded");
            getUserImage();
        });
    }

    private void getUserImage() {
        userImageViewModel.getUserImage(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, response -> {
            Log.d(TAG,  "getUserImage");

            if (response != null) {
                String imageUrl = Constant.LOCALHOST + response.getImagePath().replaceAll("\\\\", "/");

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.profile_picture)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .dontAnimate()
                        .dontTransform();

                Glide.with(getApplicationContext())
                        .load(imageUrl)
                        .apply(options)
                        .into(circleImageView);
            }
        });
    }

    public void showSnackBar() {
        snack.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snack.show();
    }

    public void hideSnackBar() {
        snack.dismiss();
    }

    private void registerNetworkBroadcastForNougat() {
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

    @Override
    public void onNetworkConnected() {
        hideSnackBar();
        showOrHideViews(View.VISIBLE);
        getMobiles();
        getLaptops();
        Log.d(TAG, "onNetworkConnected");
        getUserImage();
    }

    @Override
    public void onNetworkDisconnected() {
        showSnackBar();
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(ProductActivity.this, DetailsActivity.class);
        intent.putExtra(Constant.PRODUCT, (product));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem addMenu = menu.findItem(R.id.action_addProduct);
        boolean isAdmin = LoginUtils.getInstance(this).getUserInfo().isAdmin();
        addMenu.setVisible(isAdmin);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent cartIntent = new Intent(this, CartActivity.class);
                startActivity(cartIntent);
                return true;
            case R.id.action_addProduct:
                Intent addProductIntent = new Intent(this, AddProductActivity.class);
                startActivity(addProductIntent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void showOrHideViews(int view) {
        binding.included.content.textViewMobiles.setVisibility(view);
        binding.included.content.txtSeeAllMobiles.setVisibility(view);
        binding.included.content.textViewLaptops.setVisibility(view);
        binding.included.content.txtSeeAllLaptops.setVisibility(view);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_mobiles) {
            goToCategoryActivity("Mobile");
        } else if (id == R.id.nav_laptops) {
            goToCategoryActivity("Laptop");
        } else if (id == R.id.nav_trackOrder) {
            Intent orderIntent = new Intent(this, OrdersActivity.class);
            startActivity(orderIntent);
        } else if (id == R.id.nav_myAccount) {
            Intent accountIntent = new Intent(this, AccountActivity.class);
            startActivity(accountIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToCategoryActivity(String category) {
        Intent categoryIntent = new Intent(this, CategoryActivity.class);
        categoryIntent.putExtra(Constant.CATEGORY, category);
        startActivity(categoryIntent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            closeApplication();
        }
    }

    private void closeApplication() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.want_to_exit)
                .setPositiveButton(R.string.ok, (dialog, which) -> finish())
                .setNegativeButton(R.string.cancel, null)
                .show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this,R.color.darkGreen));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.darkGreen));
    }


    @Override
    protected void onResume() {
        super.onResume();
        productViewModel.invalidate();
        getMobiles();
        getLaptops();

    }


}
