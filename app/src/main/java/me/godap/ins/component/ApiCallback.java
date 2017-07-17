package me.godap.ins.component;

/**
 * Created by Anchorer on 2017/7/17.
 */

public interface ApiCallback<T> {
    void onSuccess(T t);
    void onError(Throwable e, String message);
}
