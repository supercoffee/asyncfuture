package com.coffeestrike.asyncfuture;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.*;

/**
 * Created by ben on 3/6/15.
 */
public class AsyncFuture<V> implements Future {

    private LinkedBlockingQueue<Handler> mHandlers;

    public AsyncFuture() {
        mHandlers = new LinkedBlockingQueue<Handler>();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    public void get(FutureCallback<V> callback){

        Looper loop = Looper.myLooper();
        if(loop == null){
            throw new RuntimeException("Thread does not have a looper");
        }

        Handler h = new Handler();

        mHandlers.offer(h);

    }
}
