package com.ptit.daily_food.data.model;

import org.json.JSONObject;

public class Instruction {

    private static final String JSON_KEY_STEP = "step";

    private String step = "";

    public Instruction(JSONObject jsonObject) {
        this.step = jsonObject.optString(JSON_KEY_STEP);
    }

    public String getStep() {
        return step;
    }
}
