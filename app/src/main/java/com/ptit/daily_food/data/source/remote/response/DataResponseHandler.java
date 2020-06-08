package com.ptit.daily_food.data.source.remote.response;

import com.ptit.daily_food.data.model.FoodDetail;

import java.util.List;

public interface DataResponseHandler {
    List<FoodDetail> getResponse(String urlRequest);
}
