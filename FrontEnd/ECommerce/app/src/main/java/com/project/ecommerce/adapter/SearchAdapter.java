package com.project.ecommerce.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.project.ecommerce.R;
import com.project.ecommerce.databinding.SearchListItemBinding;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.utils.RequestCallback;
import com.project.ecommerce.viewmodel.FromCartViewModel;
import com.project.ecommerce.viewmodel.ToCartViewModel;
import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    private final Context mContext;
    private final List<Product> productList;

    private Product currentProduct;
    private final ToCartViewModel toCartViewModel;
    private final FromCartViewModel fromCartViewModel;

    private final SearchAdapterOnClickHandler clickHandler;

    public interface SearchAdapterOnClickHandler {
        void onClick(Product product);
    }

    public SearchAdapter(Context mContext, List<Product> productList, SearchAdapterOnClickHandler clickHandler, FragmentActivity activity) {
        this.mContext = mContext;
        this.productList = productList;
        this.clickHandler = clickHandler;
        toCartViewModel = ViewModelProviders.of(activity).get(ToCartViewModel.class);
        fromCartViewModel = ViewModelProviders.of(activity).get(FromCartViewModel.class);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        SearchListItemBinding searchListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.search_list_item,parent,false);
        return new SearchViewHolder(searchListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        currentProduct = productList.get(position);
        holder.binding.txtProductName.setText(currentProduct.getProductName());

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(currentProduct.getProductPrice());
        holder.binding.txtProductPrice.setText("Rs"+formattedPrice );

        String imageUrl =  currentProduct.getProductImage().replace("localhost:8080","").replaceAll("\\\\", "/");
        Picasso.with(mContext)
                .load(Constant.LOCALHOST+imageUrl)
                .into(holder.binding.imgProductImage);

        if (currentProduct.isInCart()==1) {
            holder.binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
        }
    }

    @Override
    public int getItemCount() {
        if (productList == null) {
            return 0;
        }
        return productList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final SearchListItemBinding binding;

        private SearchViewHolder(SearchListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
            binding.imgCart.setOnClickListener(this);
            binding.addToCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            currentProduct = productList.get(position);

            switch (v.getId()) {
                case R.id.card_view:
                    clickHandler.onClick(currentProduct);
                    break;
                case R.id.imgCart:
                    toggleProductsInCart();
                    break;
                case R.id.addToCart:
                    addToCart();
                    break;
                default:
            }
        }




        private void toggleProductsInCart() {
            if (currentProduct.isInCart() != 1) {
                binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
                insertToCart(() -> {
                    currentProduct.setIsInCart(true);
                    notifyDataSetChanged();
                });
                showSnackBar("Added To Cart");
            } else {
                binding.imgCart.setImageResource(R.drawable.ic_add_shopping_cart);
                deleteFromCart(() -> {
                    currentProduct.setIsInCart(false);
                    notifyDataSetChanged();
                });
                showSnackBar("Removed From Cart");
            }
        }

        private void addToCart() {
            if (currentProduct.isInCart() != 1) {
                binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
                insertToCart(() -> {
                    currentProduct.setIsInCart(true);
                    notifyDataSetChanged();
                });
                showSnackBar("Added To Cart");
            }
        }

        private void showSnackBar(String text) {
            Snackbar.make(itemView, text, Snackbar.LENGTH_SHORT).show();
        }


        private void insertToCart(RequestCallback callback) {
            Cart cart = new Cart(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId());
            toCartViewModel.addToCart(cart, callback);
        }

        private void deleteFromCart(RequestCallback callback) {
            fromCartViewModel.removeFromCart(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId(),callback);
        }

    }
}
