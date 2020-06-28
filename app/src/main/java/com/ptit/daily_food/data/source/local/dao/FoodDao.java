package com.ptit.daily_food.data.source.local.dao;

import com.ptit.daily_food.data.model.FoodDetail;

import java.util.ArrayList;

public interface FoodDao {
    ArrayList<FoodDetail> getAllFavoriteFoods();
    ArrayList<FoodDetail>  getAllFamilyFoods();
    ArrayList<FoodDetail>  getAllPartyFoods();
    ArrayList<FoodDetail>  getAllCookingFoods();

    void insertFoodFavorite(FoodDetail foodDetail);
    void insertFoodFamily(FoodDetail foodDetail);
    void insertFoodParty(FoodDetail foodDetail);
    void insertFoodCooking(FoodDetail foodDetail);

    void deleteFoodFamily(FoodDetail foodDetail);
    void deleteFoodFavorite(FoodDetail foodDetail);
    void deleteFoodParty(FoodDetail foodDetail);
    void deleteFoodCooking(FoodDetail foodDetail);
}
