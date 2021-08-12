package com.example.mvvmarch;

import retrofit2.Call;

public interface ProductLoadable<T>{
    void load(SetProduct<T> obj, Call<T> call);
    void lezyLoad(SetProduct<T> obj, Call<T> call);
}
