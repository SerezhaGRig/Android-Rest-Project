package com.example.mvvmarch;

import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

public class ProductViewModel<T> extends ViewModel implements ProductLoadable<T> {
    private T arr=null;
    final Object lock = new Object();
    CompositeDisposable compositeDisposable = new CompositeDisposable();




    @Override
    public void load(SetProduct<T> obj, Call<T> call) {
        obj.onLoad();
        DisposableSingleObserver<T> observer = new DisposableSingleObserver<T>() {
            @Override
            public void onSuccess(T o) {
                synchronized (lock) {
                    arr = o;
                    obj.onSuccess(arr);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                obj.onError();
                e.getCause();
            }
        };

        if(compositeDisposable==null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observer);
        Single<Call<T>> single = Single.just(call);
        single.subscribeOn(Schedulers.io()).map((call_)-> call_.execute().body())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void lezyLoad(SetProduct<T> obj, Call<T> call) {
        if(arr==null)
            load(obj,call);
        else {
            synchronized (lock) {
                obj.onSuccess(arr);
            }
        }
    }
    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}

