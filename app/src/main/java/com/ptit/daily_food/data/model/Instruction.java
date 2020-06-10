package com.ptit.daily_food.data.model;

import org.json.JSONObject;

public class Instruction {

    public static final String TABLE_NAME = "tbl_instruction";
    public static final String ID = "instruction_id";
    public static final String NUMBER = "instruction_number";
    public static final String STEP = "instruction_step";
    public static final String IMAGE_INSTRUCTION = "image_instruction";
    public static final String IMAGE_INGREDIENT = "image_ingredient";

    private static final String JSON_KEY_STEP = "step";

    private String step = "";

    public Instruction(JSONObject jsonObject) {
        this.step = jsonObject.optString(JSON_KEY_STEP);
    }

    public String getStep() {
        return step;
    }
}
