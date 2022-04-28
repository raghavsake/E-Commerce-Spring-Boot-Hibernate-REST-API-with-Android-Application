package com.project.ecommerce.net;

import com.project.ecommerce.model.CartApiResponse;
import com.project.ecommerce.model.Image;
import com.project.ecommerce.model.LoginApiResponse;
import com.project.ecommerce.model.OrderApiResponse;
import com.project.ecommerce.model.ProductApiResponse;
import com.project.ecommerce.model.RegisterApiResponse;
import com.project.ecommerce.model.User;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface Api {

    @POST("users/register")
    Call<RegisterApiResponse> createUser(@Query("name") String name, @Query("gender") String gender
    ,@Query("age") int age,@Query("email") String email, @Query("password") String password);

    @GET("users/login")
    Call<LoginApiResponse> logInUser(@Query("email") String email, @Query("password") String password);

    @DELETE("users/delete")
    Call<ResponseBody> deleteAccount( @Query("userId") int userId);

    @Multipart
    @PUT("users/upload")
    Call<ResponseBody> uploadPhoto( @Part("image") MultipartBody.Part userPhoto, @Part("id") RequestBody userId);

    @PUT("users/info")
    Call<ResponseBody> updatePassword( @Query("password") String password, @Query("id") int userId);

    @Multipart
    @POST("products/insert")
    Call<ResponseBody> insertProduct(@PartMap Map<String, RequestBody> productInfo, @Part MultipartBody.Part image);

    @GET("users/getImage")
    Call<Image> getUserImage(@Query("id") int userId);


    @GET("products/")
    Call<ProductApiResponse> getProductsByCategory(@Query("category") String category, @Query("userId") int userId);

    @GET("products/search")
    Call<ProductApiResponse> searchForProduct(@Query("keyword") String keyword, @Query("userId") int userId);

    @POST("cart/add")
    Call<ResponseBody> addToCart(@Query("userId") int userId, @Query("productId") int productId);

    @DELETE("cart/remove")
    Call<ResponseBody> removeFromCart(@Query("userId") int userId, @Query("productId") int productId);

    @GET("cart/")
    Call<CartApiResponse> getProductsInCart(@Query("userId") int userId);

    @GET("orders/get")
    Call<OrderApiResponse> getOrders(@Query("userId") int userId);
//
    @POST("address/add")
    Call<ResponseBody> addShippingAddress(@Query("address") String address,
			@Query("city") String city,
			@Query("country")  String country,
			@Query("zip")  String zip,
			@Query("phone")  String phone,
			@Query("userId")  int userId,
			@Query("productId")  int productId);

    @POST("orders/add")
    Call<ResponseBody> orderProduct(@Query("status")String status,
    @Query("name_on_card")String name_on_card,
    @Query("card_number")String card_number,
    @Query("expiration_date")String expiration_date,
    @Query("userId")int userId,
    @Query("productId")int productId);
}
