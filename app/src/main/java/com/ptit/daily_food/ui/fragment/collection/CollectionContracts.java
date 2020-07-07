package com.ptit.daily_food.ui.fragment.collection;

import com.ptit.daily_food.data.model.FoodDetail;

import java.util.List;

public interface CollectionContracts {

    interface View {
        void createFavoriteFoods(List<FoodDetail> favoriteFoods);
        void createFamilyFoods(List<FoodDetail> familyFoods);
        void createPartyFoods(List<FoodDetail> partyFoods);
        void showError(Exception exception);
    }

    interface Presenter {
        void getAllFavoriteFoods();
        void getAllFamilyFoods();
        void getAllPartyFoods();
    }
}
