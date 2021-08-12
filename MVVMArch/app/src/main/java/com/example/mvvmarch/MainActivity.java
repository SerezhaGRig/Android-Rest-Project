package com.example.mvvmarch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.HandlerThread;
import android.text.method.SingleLineTransformationMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("unchecked")
public class MainActivity extends AppCompatActivity {
    static final String BASE_URL = "https://0a587443-c328-424d-8ed7-b5f3d5332883.mock.pstmn.io";
    ProductService service;
    RecyclerView recyclerView;
    ProductViewModel<List<Product>> productViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProductViewModelFactory factory = new ProductViewModelFactory();
        productViewModel = new ViewModelProvider(this,factory).get(ProductViewModel.class);
        recyclerView = findViewById(R.id.recycler);
        PrRecyclerAdapter adapter = new PrRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        service = (new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()).create(ProductService.class);
        productViewModel.lezyLoad(adapter,service.listProduct());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}