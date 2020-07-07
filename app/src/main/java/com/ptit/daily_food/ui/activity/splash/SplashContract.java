package com.ptit.daily_food.ui.activity.splash;

import com.ptit.daily_food.data.model.FoodDetail;

import java.util.List;

public interface SplashContract {

    interface View {
        void onTransportDataToHome(List<FoodDetail> data);
        void showError(Exception exception);
    }

    interface Presenter {
        void getRandomFoods();
    }
}
