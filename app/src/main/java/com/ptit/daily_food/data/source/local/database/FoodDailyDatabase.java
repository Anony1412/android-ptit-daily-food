package com.ptit.daily_food.data.source.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.model.Ingredient;
import com.ptit.daily_food.data.model.Instruction;

public class FoodDailyDatabase extends SQLiteOpenHelper {

    private static final String SQL_CREATE_TABLE_FOOD =
            "CREATE TABLE " + FoodDetail.TABLE_NAME + " (" +
                    FoodDetail.ID + " INTEGER PRIMARY KEY," +
                    FoodDetail.TITLE + " TEXT," +
                    FoodDetail.PRICE + " TEXT," +
                    FoodDetail.READY_MINUTES + " TEXT," +
                    FoodDetail.SUMMARY + " TEXT," +
                    FoodDetail.IMAGE_URL + " TEXT," +
                    FoodDetail.TYPE_FAVORITE + " TEXT," +
                    FoodDetail.TYPE_FAMILY + " TEXT," +
                    FoodDetail.TYPE_PARTY + " TEXT," +
                    FoodDetail.STATE_IS_COOKING + " TEXT);";

    private static final String SQL_CREATE_TABLE_INGREDIENT =
            "CREATE TABLE " + Ingredient.TABLE_NAME + " (" +
                    Ingredient.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Ingredient.ORIGINAL + " TEXT," +
                    FoodDetail.ID + " INTEGER," +
                    "FOREIGN KEY (" + FoodDetail.ID + ") " +
                    "REFERENCES " + FoodDetail.TABLE_NAME + "(" + FoodDetail.ID + ")" +
                    ");";

    private static final String SQL_CREATE_TABLE_INSTRUCTION =
            "CREATE TABLE " + Instruction.TABLE_NAME + " (" +
                    Instruction.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Instruction.NUMBER + " TEXT," +
                    Instruction.STEP + " TEXT," +
                    Instruction.IMAGE_INSTRUCTION + " TEXT," +
                    Instruction.IMAGE_INGREDIENT + " TEXT," +
                    FoodDetail.ID + " INTEGER," +
                    "FOREIGN KEY (" + FoodDetail.ID + ") " +
                    "REFERENCES " + FoodDetail.TABLE_NAME + "(" + FoodDetail.ID + ")" +
                    ");";

    private static final String SQL_DROP_TABLE_FOOD =
            "DROP TABLE IF EXISTS " + FoodDetail.TABLE_NAME;

    private static final String SQL_DROP_TABLE_INGREDIENT =
            "DROP TABLE IF EXISTS " + Ingredient.TABLE_NAME;

    private static final String SQL_DROP_TABLE_INSTRUCTION =
            "DROP TABLE IF EXISTS " + Instruction.TABLE_NAME;

    private static final String DB_NAME = "fooddaily-ptit-database";
    private static final Integer DB_VERSION = 1;

    public FoodDailyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FOOD);
        db.execSQL(SQL_CREATE_TABLE_INGREDIENT);
        db.execSQL(SQL_CREATE_TABLE_INSTRUCTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_FOOD);
        db.execSQL(SQL_DROP_TABLE_INGREDIENT);
        db.execSQL(SQL_DROP_TABLE_INSTRUCTION);
        onCreate(db);
    }

    public static FoodDailyDatabase getInstance(Context context) {
        return new FoodDailyDatabase(context);
    }
}
