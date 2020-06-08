package com.ptit.daily_food.data.source;

public interface OnLoadedCallback<T> {
    void onSuccess(T data);
    void onFailure(Exception exception);
}
