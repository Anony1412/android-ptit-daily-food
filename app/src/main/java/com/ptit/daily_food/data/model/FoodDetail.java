package com.ptit.daily_food.data.model;

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
}
