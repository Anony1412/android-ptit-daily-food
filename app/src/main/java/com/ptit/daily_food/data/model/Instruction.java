package com.ptit.daily_food.data.model;

import android.database.Cursor;

import org.json.JSONObject;

public class Instruction {

    public static final String TABLE_NAME = "tbl_instruction";
    public static final String ID = "instruction_id";
    public static final String NUMBER = "instruction_number";
    public static final String STEP = "instruction_step";
    public static final String IMAGE_INSTRUCTION = "image_instruction";
    public static final String IMAGE_INGREDIENT = "image_ingredient";

    private static final String JSON_KEY_STEP = "step";
    private static final String JSON_KEY_NUMBER = "number";
    private static final String TITLE_STEP = "Step ";

    private String step = "";
    private String number = "";
    private String imageInstruction = "";
    private String imageIngredient = "";

    public Instruction(JSONObject jsonObject) {
        this.step = jsonObject.optString(JSON_KEY_STEP);
        this.number = jsonObject.optString(JSON_KEY_NUMBER);
    }

    public Instruction(Cursor cursor) {
        this.number = cursor.getString(cursor.getColumnIndex(NUMBER));
        this.step = cursor.getString(cursor.getColumnIndex(STEP));
        this.imageInstruction = cursor.getString(cursor.getColumnIndex(IMAGE_INSTRUCTION));
        this.imageIngredient = cursor.getString(cursor.getColumnIndex(IMAGE_INGREDIENT));
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImageInstruction() {
        return imageInstruction;
    }

    public void setImageInstruction(String imageInstruction) {
        this.imageInstruction = imageInstruction;
    }

    public String getImageIngredient() {
        return imageIngredient;
    }

    public void setImageIngredient(String imageIngredient) {
        this.imageIngredient = imageIngredient;
    }
}
