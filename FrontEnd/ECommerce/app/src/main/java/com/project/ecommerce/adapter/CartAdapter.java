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
import com.project.ecommerce.databinding.CartListItemBinding;
import com.project.ecommerce.databinding.CartListItemBindingImpl;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.utils.Constant;
import com.project.ecommerce.utils.LoginUtils;
import com.project.ecommerce.utils.RequestCallback;
import com.project.ecommerce.viewmodel.FromCartViewModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context mContext;

    private final List<Product> productsInCart;

    private Product currentProduct;
    private final FromCartViewModel fromCartViewModel;

    private final CartAdapterOnClickHandler clickHandler;

    public interface CartAdapterOnClickHandler {
        void onClick(Product product);
    }

    public CartAdapter(Context mContext, List<Product> productInCart, CartAdapter.CartAdapterOnClickHandler clickHandler, FragmentActivity activity) {
        this.mContext = mContext;
        this.productsInCart = productInCart;
        this.clickHandler = clickHandler;
        fromCartViewModel = ViewModelProviders.of(activity).get(FromCartViewModel.class);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CartListItemBinding cartListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), com.project.ecommerce.R.layout.cart_list_item, parent, false);
        return new CartViewHolder((CartListItemBindingImpl) cartListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        currentProduct = productsInCart.get(position);

        System.out.println(currentProduct.getProductName());
        holder.binding.txtProductName.setText(currentProduct.getProductName());

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(currentProduct.getProductPrice());
        holder.binding.txtProductPrice.setText("Rs"+ formattedPrice );

        String imageUrl =  currentProduct.getProductImage().replace("localhost:8080","").replaceAll("\\\\", "/");
        Picasso.with(mContext)
                .load(Constant.LOCALHOST+imageUrl)
                .into(holder.binding.imgProductImage);


    }

    @Override
    public int getItemCount() {
        if (productsInCart == null) {
            return 0;
        }
        return productsInCart.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final CartListItemBindingImpl binding;

        private CartViewHolder(CartListItemBindingImpl binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);

            binding.imgCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            currentProduct = productsInCart.get(position);

            switch (v.getId()) {
                case R.id.card_view:
                    clickHandler.onClick(currentProduct);
                    break;
                case R.id.imgCart:
                    deleteProductsInCart();
                    break;
                default:
            }
        }



        private void deleteProductsInCart() {
            deleteFromCart(() -> {
                currentProduct.setIsInCart(false);
                notifyDataSetChanged();
            });
            productsInCart.remove(getBindingAdapterPosition());
            notifyItemRemoved(getBindingAdapterPosition());
            notifyItemRangeChanged(getBindingAdapterPosition(), productsInCart.size());
            showSnackBar("Removed From Cart");
        }

        private void showSnackBar(String text) {
            Snackbar.make(itemView, text, Snackbar.LENGTH_SHORT).show();
        }


        private void deleteFromCart(RequestCallback callback) {
            fromCartViewModel.removeFromCart(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId(), callback);
        }
    }
}
