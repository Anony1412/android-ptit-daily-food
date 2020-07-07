package com.ptit.daily_food.data.source.local.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.model.Ingredient;
import com.ptit.daily_food.data.model.Instruction;
import com.ptit.daily_food.data.source.local.database.FoodDailyDatabase;

import java.util.ArrayList;
import java.util.List;

public class FoodDaoImpl implements FoodDao {

    private static final String CHARACTER_EQUAL = "=";
    private static final String KEY_IN_COLLECTION = "1";
    private static final String KEY_NOT_IN_COLLECTION = "0";

    private FoodDailyDatabase foodDailyDatabase;
    private SQLiteDatabase database;

    public FoodDaoImpl(FoodDailyDatabase foodDailyDatabase) {
        this.foodDailyDatabase = foodDailyDatabase;
        database = this.foodDailyDatabase.getWritableDatabase();
    }

    public static FoodDao getInstance(FoodDailyDatabase foodDailyDatabase) {
        return new FoodDaoImpl(foodDailyDatabase);
    }

    @Override
    public ArrayList<FoodDetail> getAllFavoriteFoods() {
        return getAllFoodsByCollection(FoodDetail.TYPE_FAVORITE);
    }

    @Override
    public ArrayList<FoodDetail> getAllFamilyFoods() {
        return getAllFoodsByCollection(FoodDetail.TYPE_FAMILY);
    }

    @Override
    public ArrayList<FoodDetail> getAllPartyFoods() {
        return getAllFoodsByCollection(FoodDetail.TYPE_PARTY);
    }

    @Override
    public ArrayList<FoodDetail> getAllCookingFoods() {
        return getAllFoodsByCollection(FoodDetail.STATE_IS_COOKING);
    }

    @Override
    public void insertFoodFavorite(FoodDetail foodDetail) {
        resolveInsert(foodDetail, FoodDetail.TYPE_FAVORITE);
    }

    @Override
    public void insertFoodFamily(FoodDetail foodDetail) {
        resolveInsert(foodDetail, FoodDetail.TYPE_FAMILY);
    }

    @Override
    public void insertFoodParty(FoodDetail foodDetail) {
        resolveInsert(foodDetail, FoodDetail.TYPE_PARTY);
    }

    @Override
    public void insertFoodCooking(FoodDetail foodDetail) {
        resolveInsert(foodDetail, FoodDetail.STATE_IS_COOKING);
    }

    @Override
    public void deleteFoodFamily(FoodDetail foodDetail) {
        deleteFood(foodDetail, FoodDetail.TYPE_FAMILY);
    }

    @Override
    public void deleteFoodFavorite(FoodDetail foodDetail) {
        deleteFood(foodDetail, FoodDetail.TYPE_FAVORITE);
    }

    @Override
    public void deleteFoodParty(FoodDetail foodDetail) {
        deleteFood(foodDetail, FoodDetail.TYPE_PARTY);
    }

    @Override
    public void deleteFoodCooking(FoodDetail foodDetail) {
        deleteFood(foodDetail, FoodDetail.STATE_IS_COOKING);
    }

    private ArrayList<FoodDetail> getAllFoodsByCollection(String collectionName) {
        ArrayList<FoodDetail> foods = new ArrayList<>();
        String selection = collectionName + CHARACTER_EQUAL + KEY_IN_COLLECTION;
        Cursor cursor = database.query(
                FoodDetail.TABLE_NAME,
                null,
                selection,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                foods.add(new FoodDetail(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        for (FoodDetail food : foods) {
            food.setIngredients(getFoodIngredient(food.getId()));
            food.setInstructions(getFoodInstruction(food.getId()));
        }
        return foods;
    }

    private ArrayList<Ingredient> getFoodIngredient(String foodId) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        String selection = FoodDetail.ID + CHARACTER_EQUAL + foodId;
        Cursor cursor = database.query(
                Ingredient.TABLE_NAME,
                null,
                selection,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String original = cursor.getString(cursor.getColumnIndex(Ingredient.ORIGINAL));
                ingredients.add(new Ingredient(original));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return ingredients;
    }

    private ArrayList<Instruction> getFoodInstruction(String foodId) {
        ArrayList<Instruction> instructions = new ArrayList<>();
        String selection = FoodDetail.ID + CHARACTER_EQUAL + foodId;
        Cursor cursor = database.query(
                Instruction.TABLE_NAME,
                null,
                selection,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                instructions.add(new Instruction(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return instructions;
    }

    private void resolveInsert(FoodDetail foodDetail, String collectionName) {
        if (checkFoodInDatabase(foodDetail)) {
            updateFood(foodDetail, collectionName);
        } else {
            insertFood(foodDetail, collectionName);
        }
    }

    private void insertFood(FoodDetail foodDetail, String collectionName) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(FoodDetail.ID, foodDetail.getId());
        contentValues.put(FoodDetail.TITLE, foodDetail.getTitle());
        contentValues.put(FoodDetail.PRICE, foodDetail.getPrice());
        contentValues.put(FoodDetail.READY_MINUTES, foodDetail.getReadyMinutes());
        contentValues.put(FoodDetail.SUMMARY, foodDetail.getSummary());
        contentValues.put(FoodDetail.IMAGE_URL, foodDetail.getImageUrl());
        contentValues.put(collectionName, KEY_IN_COLLECTION);

        database.insert(FoodDetail.TABLE_NAME, null, contentValues);
        insertToTableIngredient(foodDetail);
        insertToTableInstruction(foodDetail);
    }

    private void insertToTableInstruction(FoodDetail foodDetail) {
        List<Instruction> instructions = foodDetail.getInstructions();
        for (Instruction instruction : instructions) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Instruction.NUMBER, instruction.getNumber());
            contentValues.put(Instruction.STEP, instruction.getStep());
            contentValues.put(Instruction.IMAGE_INSTRUCTION, instruction.getImageInstruction());
            contentValues.put(Instruction.IMAGE_INGREDIENT, instruction.getImageIngredient());
            contentValues.put(FoodDetail.ID, foodDetail.getId());
            database.insert(Instruction.TABLE_NAME, null, contentValues);
        }
    }

    private void insertToTableIngredient(FoodDetail foodDetail) {
        List<Ingredient> ingredients = foodDetail.getIngredients();
        for (Ingredient ingredient : ingredients) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Ingredient.ORIGINAL, ingredient.getOriginal());
            contentValues.put(FoodDetail.ID, foodDetail.getId());
            database.insert(Ingredient.TABLE_NAME, null, contentValues);
        }
    }

    private void updateFood(FoodDetail foodDetail, String collectionName) {
        String whereClause = FoodDetail.ID + CHARACTER_EQUAL + foodDetail.getId();
        ContentValues contentValues = new ContentValues();
        contentValues.put(collectionName, KEY_IN_COLLECTION);
        database.update(FoodDetail.TABLE_NAME, contentValues, whereClause, null);
    }

    private boolean checkFoodInDatabase(FoodDetail foodDetail) {
        boolean isFoodExist = false;
        ArrayList<String> foodIds = getAllFoodIds();
        for (String foodId : foodIds) {
            if (foodId.compareTo(foodDetail.getId()) == 0) {
                isFoodExist = true;
                break;
            }
        }
        return isFoodExist;
    }

    private ArrayList<String> getAllFoodIds() {
        ArrayList<String> foodIds = new ArrayList<String>();
        Cursor cursor = database.query(
                FoodDetail.TABLE_NAME,
                null, null, null, null, null, null
        );
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndex(FoodDetail.ID));
                cursor.moveToNext();
            }
        }
        assert cursor != null;
        cursor.close();
        return foodIds;
    }

    private void deleteFood(FoodDetail foodDetail, String collectionName) {
        String whereClause = FoodDetail.ID + CHARACTER_EQUAL + foodDetail.getId();
        ContentValues contentValues = new ContentValues();
        contentValues.put(collectionName, KEY_NOT_IN_COLLECTION);
        database.update(FoodDetail.TABLE_NAME, contentValues, whereClause, null);
    }
}
