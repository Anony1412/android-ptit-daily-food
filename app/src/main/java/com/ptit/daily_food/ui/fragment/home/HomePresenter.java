package com.ptit.daily_food.ui.fragment.home;

import com.ptit.daily_food.data.model.FoodDetail;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements HomeContracts.Presenter {

    private HomeContracts.View homeView;

    public HomePresenter(HomeContracts.View homeView) {
        this.homeView = homeView;
    }

    @Override
    public void createDailyMenu(List<FoodDetail> otherFoods) {
        ArrayList<FoodDetail> dailyFoods = new ArrayList<>();
        for (int i = 0; i < otherFoods.size(); i++) {
            if (i % 4 == 0) dailyFoods.add(otherFoods.get(i));
        }
        homeView.showDailyMenu(dailyFoods);
    }
}
