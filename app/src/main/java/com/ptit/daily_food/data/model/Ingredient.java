package com.ptit.daily_food.data.model;

import org.json.JSONObject;

public class Ingredient {

    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_NAME = "name";
    private static final String JSON_KEY_ORIGINAL = "original";

    public static final String TABLE_NAME = "tbl_ingredient";
    public static final String ID = "ingredient_id";
    public static final String ORIGINAL = "ingredient_original";

    private String id = "";
    private String name = "";
    private String original = "";

    public Ingredient(JSONObject jsonObject) {
        this.id = jsonObject.optString(JSON_KEY_ID);
        this.name = jsonObject.optString(JSON_KEY_NAME);
        this.original = jsonObject.optString(JSON_KEY_ORIGINAL);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOriginal() {
        return original;
    }
}
