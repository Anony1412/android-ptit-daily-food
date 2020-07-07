package com.ptit.daily_food.data.source;

import com.ptit.daily_food.data.model.FoodDetail;
import java.util.List;

public interface RecipeDataSource {

    interface Local {
        void getAllFavoriteFoods(OnLoadedCallback<List<FoodDetail>> callback);
        void getAllPartyFoods(OnLoadedCallback<List<FoodDetail>> callback);
        void getAllFamilyFoods(OnLoadedCallback<List<FoodDetail>> callback);
        void getAllCookingFoods(OnLoadedCallback<List<FoodDetail>> callback);
        void addToFavorite(FoodDetail foodDetail);
        void addToFamily(FoodDetail foodDetail);
        void addToParty(FoodDetail foodDetail);
        void addToCooking(FoodDetail foodDetail);
        void deleteFoodFromFavorite(FoodDetail foodDetail);
        void deleteFoodFromFamily(FoodDetail foodDetail);
        void deleteFoodFromParty(FoodDetail foodDetail);
        void deleteFoodFromCooking(FoodDetail foodDetail);
    }

    interface Remote {
        void getRandomRecipes(OnLoadedCallback<List<FoodDetail>> callback);
    }
}
