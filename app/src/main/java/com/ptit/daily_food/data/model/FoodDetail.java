package com.ptit.daily_food.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.ptit.daily_food.data.source.local.dao.FoodDao;
import com.ptit.daily_food.data.source.local.dao.FoodDaoImpl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodDetail {

    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_TITLE = "title";
    private static final String JSON_KEY_PRICE = "pricePerServing";
    private static final String JSON_KEY_READY_MINUTES = "readyInMinutes";
    private static final String JSON_KEY_SUMMARY = "summary";
    private static final String JSON_KEY_IMAGE = "image";
    private static final String JSON_KEY_INGREDIENTS = "extendedIngredients";
    private static final String JSON_KEY_INSTRUCTIONS = "analyzedInstructions";
    private static final String JSON_KEY_STEPS = "steps";

    public static final String TABLE_NAME = "tbl_food";
    public static final String ID = "food_id";
    public static final String TITLE = "food_title";
    public static final String PRICE = "food_price";
    public static final String READY_MINUTES = "food_ready_minutes";
    public static final String SUMMARY = "food_summary";
    public static final String IMAGE_URL = "food_image_url";
    public static final String TYPE_FAVORITE = "is_favorite";
    public static final String TYPE_FAMILY = "is_family";
    public static final String TYPE_PARTY = "is_party";
    public static final String STATE_IS_COOKING = "is_cooking";

    private String id = "";
    private String title = "";
    private String price = "";
    private String readyMinutes = "";
    private String summary = "";
    private String imageUrl = "";
    private List<Ingredient> ingredients = null;
    private List<Instruction> instructions = null;

    public FoodDetail(JSONObject jsonObject) {
        this.id = jsonObject.optString(JSON_KEY_ID);
        this.title = jsonObject.optString(JSON_KEY_TITLE);
        this.price = jsonObject.optString(JSON_KEY_PRICE);
        this.readyMinutes = jsonObject.optString(JSON_KEY_READY_MINUTES);
        this.summary = jsonObject.optString(JSON_KEY_SUMMARY);
        this.imageUrl = jsonObject.optString(JSON_KEY_IMAGE);
        this.ingredients = getIngredient(jsonObject);
        this.instructions = getInstructions(jsonObject);
    }

    public FoodDetail(Cursor cursor) {
        this.id = cursor.getString(cursor.getColumnIndex(ID));
        this.title = cursor.getString(cursor.getColumnIndex(TITLE));
        this.price = cursor.getString(cursor.getColumnIndex(PRICE));
        this.readyMinutes = cursor.getString(cursor.getColumnIndex(READY_MINUTES));
        this.summary = cursor.getString(cursor.getColumnIndex(SUMMARY));
        this.imageUrl = cursor.getString(cursor.getColumnIndex(IMAGE_URL));
    }

    private List<Instruction> getInstructions(JSONObject jsonObject) {
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        JSONArray instructionList = jsonObject.optJSONArray(JSON_KEY_INSTRUCTIONS);
        if (instructionList != null) {
            JSONObject instruction = instructionList.optJSONObject(0);
            if (instruction != null) {
                JSONArray stepList = instruction.optJSONArray(JSON_KEY_STEPS);
                if (stepList != null) {
                    for (int i = 0; i < stepList.length(); i++) {
                        JSONObject stepItem = stepList.optJSONObject(i);
                        instructions.add(new Instruction(stepItem));
                    }
                }
            }
        }
        return instructions;
    }

    private ArrayList<Ingredient> getIngredient(JSONObject jsonObject) {
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        JSONArray ingredientList = jsonObject.optJSONArray(JSON_KEY_INGREDIENTS);
        if (ingredientList != null) {
            for (int i = 0; i < ingredientList.length(); i++) {
                JSONObject item = ingredientList.optJSONObject(i);
                ingredients.add(new Ingredient(item));
            }
        }
        return ingredients;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getReadyMinutes() {
        return readyMinutes;
    }

    public String getSummary() {
        return summary;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setReadyMinutes(String readyMinutes) {
        this.readyMinutes = readyMinutes;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }
}
