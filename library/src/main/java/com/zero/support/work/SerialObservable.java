package com.zero.support.work;

import java.util.concurrent.Executor;

public class SerialObservable<T> extends Observable<T> {
    private Executor mExecutor;

    public SerialObservable() {
        this(AppExecutor.current());
    }

    public SerialObservable(Executor executor) {
        mExecutor = executor;
    }

    public synchronized SerialObservable<T> observeOn(Executor executor) {
        mExecutor = executor;
        return this;
    }

    @Override
    protected void performDispatch(Observer<T> observer, Object value, int version) {
        mExecutor.execute(new PostRunnable(observer, value, version, false, false));
    }

    @Override
    protected synchronized void performObserve(Observer<T> observer, boolean weak) {
        mExecutor.execute(new PostRunnable(observer, null, 0, weak, true));
    }

    protected void onPostPerformObserver(Observer<T> observer, boolean weak) {
        dispatchObserver(observer, weak);
    }

    protected void onPostPerformDispatch(Observer<T> observer, Object value, int version) {
        dispatchValue(observer, value, version);
    }

    private class PostRunnable implements Runnable {
        private final Object mPostValue;
        private final Observer<T> mObserver;
        private final int mVersion;
        private final boolean mWeak;
        private final boolean mObserve;

        public PostRunnable(Observer<T> observer, Object value, int version, boolean weak, boolean observe) {
            mObserver = observer;
            mPostValue = value;
            mVersion = version;
            mWeak = weak;
            mObserve = observe;
        }


        @Override
        public void run() {
            if (mObserve) {
                onPostPerformObserver(mObserver, mWeak);
            } else {
                onPostPerformDispatch(mObserver, mPostValue, mVersion);
            }
        }
    }

}
