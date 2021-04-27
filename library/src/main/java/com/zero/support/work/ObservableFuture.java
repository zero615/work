package com.zero.support.work;

import android.os.ConditionVariable;

public class ObservableFuture<T> implements Observer<T> {
    private volatile ConditionVariable variable = new ConditionVariable();
    private Observable<T> observable;
    private volatile T value;

    public ObservableFuture(Observable<T> observable) {
        this.observable = observable;
        value = observable.getValue();
        observable.observe(this);
    }

    public T getValue() {
        if (value != null) {
            return value;
        }
        variable.block();
        return value;
    }

    @Override
    public void onChanged(T t) {
        observable.remove(this);
        value = t;
        variable.open();
    }
}
