package com.ptit.daily_food.ui.activity.food;

import com.ptit.daily_food.data.model.FoodDetail;

public interface FoodDetailContract {

    interface Presenter {
        void addFoodToFavorite(FoodDetail foodDetail);
        void addFoodToFamily(FoodDetail foodDetail);
        void addFoodToParty(FoodDetail foodDetail);
        void addFoodToCooking(FoodDetail foodDetail);
        void deleteFoodFromCooking(FoodDetail foodDetail);
    }
}
