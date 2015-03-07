package com.coffeestrike.asyncfuture;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.*;

/**
 * Created by ben on 3/6/15.
 */
public class AsyncFuture<V> implements Future {

    private LinkedBlockingQueue<Handler> mHandlers;

    private V mObject;

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

    public void get(final FutureCallback<V> callback){

        //if object is already available, callback immediately
        if(mObject != null){
            callback.onComplete(mObject);
            return;
        }

        //object is not available at this time, configure a handler
        // to fire the callback later
        Looper loop = Looper.myLooper();
        if(loop == null){
            throw new RuntimeException("Thread does not have a looper.");
        }

        /*
            Creating a handler and placing the callback inside the handle message
            methods ensures that the callback is always made on the same thread that
            the get call was made on.
         */
        Handler h = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                callback.onComplete(mObject);
            }
        };

        mHandlers.offer(h);
    }

    public synchronized void set(V object){

        if(mObject != null){
            throw new RuntimeException("Object is already set.");
        }

        mObject = object;

        //notify all the handlers that the object is available.
        while(mHandlers.size() > 0){
            Handler h = mHandlers.poll();
            h.sendEmptyMessage(0);
        }
    }

}
