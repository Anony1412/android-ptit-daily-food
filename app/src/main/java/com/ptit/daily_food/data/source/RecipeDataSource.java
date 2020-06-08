package com.ptit.daily_food.data.source;

import com.ptit.daily_food.data.model.FoodDetail;
import java.util.List;

public interface RecipeDataSource {
    interface Remote {
        void getRandomRecipes(OnLoadedCallback<List<FoodDetail>> callback);
    }
}
