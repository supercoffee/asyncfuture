package com.coffeestrike.asyncfuture;

/**
 * Created by ben on 3/6/15.
 */
public interface FutureCallback<V> {

    public void onComplete(V obj);
}
