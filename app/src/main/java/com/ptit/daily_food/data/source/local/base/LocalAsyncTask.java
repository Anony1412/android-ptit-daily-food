package com.ptit.daily_food.data.source.local.base;

import android.os.AsyncTask;

import com.ptit.daily_food.data.source.OnLoadedCallback;

public class LocalAsyncTask <P, T> extends AsyncTask<P, Void, T> {

    private static final String MESSAGE_NULL_RESULT =
            "com.ptit.daily_food.data.source.local.base.LocalAsyncTask.MESSAGE_NULL_RESULT";

    private LocalDataHandler<P, T> handler;
    private OnLoadedCallback<T> callback;

    private Exception exception = null;


    public LocalAsyncTask (LocalDataHandler<P, T> handler, OnLoadedCallback<T> callback) {
        this.handler = handler;
        this.callback = callback;
    }

    @SafeVarargs
    @Override
    protected final T doInBackground( P... params) {
        try {
            return handler.execute(params[0]);
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
        if (result != null) {
            callback.onSuccess(result);
        } else {
            if (exception != null) callback.onFailure(exception);
            else callback.onFailure(new Exception(MESSAGE_NULL_RESULT));
        }
    }
}
