package com.ptit.daily_food.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Instruction implements Parcelable {

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

    protected Instruction(Parcel in) {
        step = in.readString();
        number = in.readString();
        imageInstruction = in.readString();
        imageIngredient = in.readString();
    }

    public static final Creator<Instruction> CREATOR = new Creator<Instruction>() {
        @Override
        public Instruction createFromParcel(Parcel in) {
            return new Instruction(in);
        }

        @Override
        public Instruction[] newArray(int size) {
            return new Instruction[size];
        }
    };

    public String getStepNumber() {
        return "Step " + this.number;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(step);
        dest.writeString(number);
        dest.writeString(imageInstruction);
        dest.writeString(imageIngredient);
    }
}
