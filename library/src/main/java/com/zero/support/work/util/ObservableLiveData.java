package com.zero.support.work.util;


import androidx.lifecycle.MutableLiveData;

import com.zero.support.work.Observable;
import com.zero.support.work.Observer;


@SuppressWarnings("ALL")
public class ObservableLiveData<T> extends MutableLiveData<T> {
    private final Observable<T> observable;
    private final Observer<T> observer = new Observer<T>() {
        @Override
        public void onChanged(T t) {
            postValue(t);
        }
    };

    public ObservableLiveData(Observable<T> observable) {
        this.observable = observable;
        this.observable.observe(observer,true);
    }
}
