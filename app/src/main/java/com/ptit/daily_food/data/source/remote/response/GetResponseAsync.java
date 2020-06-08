package com.ptit.daily_food.data.source.remote.response;

import android.os.AsyncTask;

import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.source.OnLoadedCallback;

import java.util.ArrayList;
import java.util.List;

public class GetResponseAsync extends AsyncTask<String, Void, List<FoodDetail>> {

    private Exception exception = null;

    private DataResponseHandler dataResponseHandler;
    private OnLoadedCallback<List<FoodDetail>> callback;

    public GetResponseAsync(
            DataResponseHandler dataResponseHandler,
            OnLoadedCallback<List<FoodDetail>> callback
    ) {
        this.dataResponseHandler = dataResponseHandler;
        this.callback = callback;
    }

    @Override
    protected List<FoodDetail> doInBackground(String... strings) {
        List<FoodDetail> result = new ArrayList<>();
        try {
            result = dataResponseHandler.getResponse(strings[0]);
        } catch (Exception exception) {
            this.exception = exception;
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<FoodDetail> foodDetails) {
        super.onPostExecute(foodDetails);
        if (foodDetails != null) {
            callback.onSuccess(foodDetails);
        } else {
            callback.onFailure(exception);
        }
    }
}
