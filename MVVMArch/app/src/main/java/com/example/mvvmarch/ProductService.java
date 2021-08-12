package com.example.mvvmarch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    @GET("product/all")
    Call<List<Product>> listProduct();
    @GET("product/{id}")
    Call<Product> product(@Path("id") int id);
}
