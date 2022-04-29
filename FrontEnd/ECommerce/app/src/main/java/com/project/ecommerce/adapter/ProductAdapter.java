package com.project.ecommerce.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.project.ecommerce.R;
import com.project.ecommerce.databinding.ProductListItemBinding;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.utils.RequestCallback;
import com.project.ecommerce.utils.Utils;
import com.project.ecommerce.viewmodel.FromCartViewModel;
import com.project.ecommerce.viewmodel.ToCartViewModel;
import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;

public class ProductAdapter extends PagedListAdapter<Product, ProductAdapter.ProductViewHolder> {

    private final Context mContext;
    private Product product;
    private final ToCartViewModel toCartViewModel;
    private final FromCartViewModel fromCartViewModel;

    private final ProductAdapterOnClickHandler clickHandler;

    public interface ProductAdapterOnClickHandler {
        void onClick(Product product);
    }

    public ProductAdapter(Context mContext, ProductAdapterOnClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
        this.clickHandler = clickHandler;
        toCartViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(ToCartViewModel.class);
        fromCartViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(FromCartViewModel.class);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ProductListItemBinding productListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_list_item, parent, false);
        return new ProductViewHolder(productListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        product = getItem(position);

        if (product != null) {
            String productName = product.getProductName();
            holder.binding.txtProductName.setText(productName);

            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedPrice = formatter.format(product.getProductPrice());
            holder.binding.txtProductPrice.setText("Rs"+formattedPrice );

            String imageUrl =  product.getProductImage().replace("localhost:8080","").replaceAll("\\\\", "/");
            Picasso.with(mContext)
                    .load(Constant.LOCALHOST+imageUrl)
                    .into(holder.binding.imgProductImage);

//            Glide.with(mContext)
//                    .load(imageUrl)
//                    .into(holder.binding.imgProductImage);


            if (product.isInCart() == 1) {
                holder.binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
            }

        } else {
            Toast.makeText(mContext, "Product is null", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void notifyOnInsertedItem(int position) {
        notifyItemInserted(position);
        notifyItemRangeInserted(position, getCurrentList().size()-1);
        notifyDataSetChanged();
    }

    private static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldProduct, @NonNull Product newProduct) {
            return oldProduct.getProductName().equals(newProduct.getProductName());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Product oldProduct, @NonNull Product newProduct) {
            return oldProduct.equals(newProduct);
        }
    };

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ProductListItemBinding binding;

        private ProductViewHolder(ProductListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);

            binding.imgCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            product = getItem(position);

            switch (v.getId()) {
                case R.id.card_view:
                    clickHandler.onClick(product);
                    break;
                case R.id.imgCart:
                    toggleProductsInCart();
                    break;
                default:
            }
        }


        private void toggleProductsInCart() {
            if (product.isInCart() != 1) {
                binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
                insertToCart(() -> {
                    product.setIsInCart(true);
                    notifyDataSetChanged();
                });
                showSnackBar("Added To Cart");
            } else {
                binding.imgCart.setImageResource(R.drawable.ic_add_shopping_cart);
                deleteFromCart(() -> {
                    product.setIsInCart(false);
                    notifyDataSetChanged();
                });
                showSnackBar("Removed From Cart");
            }
        }

        private void showSnackBar(String text) {
            Snackbar.make(itemView, text, Snackbar.LENGTH_SHORT).show();
        }


        private void insertToCart(RequestCallback callback) {
            Cart cart = new Cart(LoginUtils.getInstance(mContext).getUserInfo().getId(), product.getProductId());
            toCartViewModel.addToCart(cart, callback);
        }

        private void deleteFromCart(RequestCallback callback) {
            fromCartViewModel.removeFromCart(LoginUtils.getInstance(mContext).getUserInfo().getId(), product.getProductId(), callback);
        }

    }


}