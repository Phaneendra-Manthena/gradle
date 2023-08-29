package com.bhargo.user.interfaces;

public interface Callback<T> {
    void onSuccess(T result);

    void onFailure(Throwable throwable);
}
