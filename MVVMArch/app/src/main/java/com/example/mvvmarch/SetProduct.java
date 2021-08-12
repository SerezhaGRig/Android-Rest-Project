package com.example.mvvmarch;

import java.util.ArrayList;

import retrofit2.Call;

public interface SetProduct<T>{
    void onSuccess(T products);
    void onLoad();
    void onError();
}
