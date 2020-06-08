package com.ptit.daily_food.data.source.remote;

import com.ptit.daily_food.BuildConfig;
import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.source.OnLoadedCallback;
import com.ptit.daily_food.data.source.RecipeDataSource;
import com.ptit.daily_food.data.source.remote.response.GetResponseAsync;
import com.ptit.daily_food.data.source.remote.response.RecipeResponseHandler;

import java.util.List;

public class RecipeRemoteDataSource implements RecipeDataSource.Remote {
    @Override
    public void getRandomRecipes(OnLoadedCallback<List<FoodDetail>> callback) {
        String urlRequest =
                "https://api.spoonacular.com/" +
                        "recipes/random?" +
                        "number=25&limitLicense=true&apiKey=" +
                        BuildConfig.API_KEY;
        GetResponseAsync async = new GetResponseAsync(new RecipeResponseHandler(), callback);
        async.execute(urlRequest);
    }
}
