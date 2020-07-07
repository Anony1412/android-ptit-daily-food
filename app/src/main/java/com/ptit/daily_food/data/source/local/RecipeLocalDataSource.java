package com.ptit.daily_food.data.source.local;

import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.source.OnLoadedCallback;
import com.ptit.daily_food.data.source.RecipeDataSource;
import com.ptit.daily_food.data.source.local.base.LocalAsyncTask;
import com.ptit.daily_food.data.source.local.base.LocalDataHandler;
import com.ptit.daily_food.data.source.local.dao.FoodDao;

import java.util.List;

public class RecipeLocalDataSource implements RecipeDataSource.Local {

    private static final String EMPTY_PARAMS = "com.ptit.daily_food.data.source.local.EMPTY_PARAMS";

    private FoodDao foodDao;

    public RecipeLocalDataSource (FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    public static RecipeLocalDataSource getInstance (FoodDao foodDao) {
        return new RecipeLocalDataSource(foodDao);
    }

    @Override
    public void getAllFavoriteFoods(OnLoadedCallback<List<FoodDetail>> callback) {
        LocalDataHandler<String, List<FoodDetail>> handler =
                new LocalDataHandler<String, List<FoodDetail>>() {
                    @Override
                    public List<FoodDetail> execute(String params) throws Exception {
                        return foodDao.getAllFavoriteFoods();
                    }
                };
        LocalAsyncTask localAsyncTask = new LocalAsyncTask(handler, callback);
        localAsyncTask.execute(EMPTY_PARAMS);
    }

    @Override
    public void getAllPartyFoods(OnLoadedCallback<List<FoodDetail>> callback) {
        LocalDataHandler<String, List<FoodDetail>> handler =
                new LocalDataHandler<String, List<FoodDetail>>() {
                    @Override
                    public List<FoodDetail> execute(String params) throws Exception {
                        return foodDao.getAllPartyFoods();
                    }
                };
        LocalAsyncTask localAsyncTask = new LocalAsyncTask(handler, callback);
        localAsyncTask.execute(EMPTY_PARAMS);
    }

    @Override
    public void getAllFamilyFoods(OnLoadedCallback<List<FoodDetail>> callback) {
        LocalDataHandler<String, List<FoodDetail>> handler =
                new LocalDataHandler<String, List<FoodDetail>>() {
                    @Override
                    public List<FoodDetail> execute(String params) throws Exception {
                        return foodDao.getAllFamilyFoods();
                    }
                };
        LocalAsyncTask localAsyncTask = new LocalAsyncTask(handler, callback);
        localAsyncTask.execute(EMPTY_PARAMS);
    }

    @Override
    public void getAllCookingFoods(OnLoadedCallback<List<FoodDetail>> callback) {
        LocalDataHandler<String, List<FoodDetail>> handler =
                new LocalDataHandler<String, List<FoodDetail>>() {
                    @Override
                    public List<FoodDetail> execute(String params) throws Exception {
                        return foodDao.getAllCookingFoods();
                    }
                };
        LocalAsyncTask localAsyncTask = new LocalAsyncTask(handler, callback);
        localAsyncTask.execute(EMPTY_PARAMS);
    }

    @Override
    public void addToFavorite(FoodDetail foodDetail) {
        foodDao.insertFoodFavorite(foodDetail);
    }

    @Override
    public void addToFamily(FoodDetail foodDetail) {
        foodDao.insertFoodFamily(foodDetail);
    }

    @Override
    public void addToParty(FoodDetail foodDetail) {
        foodDao.insertFoodParty(foodDetail);
    }

    @Override
    public void addToCooking(FoodDetail foodDetail) {
        foodDao.insertFoodCooking(foodDetail);
    }

    @Override
    public void deleteFoodFromFavorite(FoodDetail foodDetail) {
        foodDao.deleteFoodFavorite(foodDetail);
    }

    @Override
    public void deleteFoodFromFamily(FoodDetail foodDetail) {
        foodDao.deleteFoodFamily(foodDetail);
    }

    @Override
    public void deleteFoodFromParty(FoodDetail foodDetail) {
        foodDao.deleteFoodParty(foodDetail);
    }

    @Override
    public void deleteFoodFromCooking(FoodDetail foodDetail) {
        foodDao.deleteFoodCooking(foodDetail);
    }
}
