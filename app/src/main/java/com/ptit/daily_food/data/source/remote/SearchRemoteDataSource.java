package com.ptit.daily_food.data.source.remote;

import com.ptit.daily_food.BuildConfig;
import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.source.OnLoadedCallback;
import com.ptit.daily_food.data.source.SearchDataSource;
import com.ptit.daily_food.data.source.remote.response.GetResponseAsync;
import com.ptit.daily_food.data.source.remote.response.RecipeByIdResponseHandler;
import com.ptit.daily_food.data.source.remote.response.SearchResponseHandler;

import java.util.List;

public class SearchRemoteDataSource implements SearchDataSource.Remote {
    @Override
    public void searchRecipeComplex(String keyword, OnLoadedCallback<List<FoodDetail>> callback) {
        String urlRequest =
                "https://api.spoonacular.com/" +
                        "recipes/complexSearch?" +
                        keyword + "&number=10&apiKey=" + BuildConfig.API_KEY;

        GetResponseAsync async = new GetResponseAsync(new SearchResponseHandler(), callback);
        async.execute(urlRequest);
    }

    @Override
    public void searchRecipeById(String foodId, OnLoadedCallback<List<FoodDetail>> callback) {
        String urlRequest =
                "https://api.spoonacular.com/" +
                        "recipes/" + foodId + "/information?" +
                        "includeNutrition=false&apiKey=" + BuildConfig.API_KEY;
        GetResponseAsync async = new GetResponseAsync(new RecipeByIdResponseHandler(), callback);
        async.execute(urlRequest);
    }
}
