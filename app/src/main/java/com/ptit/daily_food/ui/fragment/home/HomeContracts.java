package com.ptit.daily_food.ui.fragment.home;

import com.ptit.daily_food.data.model.FoodDetail;

import java.util.List;

public interface HomeContracts {

    interface View {
        void showDailyMenu(List<FoodDetail> dailyFoods);
    }

    interface Presenter {
        void createDailyMenu(List<FoodDetail> otherFoods);
    }
}
