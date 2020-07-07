package com.ptit.daily_food.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Ingredient implements Parcelable {

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

    public Ingredient(String original) {
        this.original = original;
    }

    protected Ingredient(Parcel in) {
        id = in.readString();
        name = in.readString();
        original = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOriginal() {
        return original;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(original);
    }
}
