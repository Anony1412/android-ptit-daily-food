package com.ptit.daily_food.data.source.remote.response;

import android.provider.SyncStateContract;

import com.ptit.daily_food.data.model.FoodDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecipeResponseHandler implements DataResponseHandler {

    private static final String METHOD_GET = "GET";
    @Override
    public List<FoodDetail> getResponse(String urlRequest) {
        String strJson = "";
        try {
             strJson = handleConnect(new URL(urlRequest));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return convertJSONToFood(strJson, "recipes");
    }

    private String handleConnect(URL url) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod(METHOD_GET);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    result.append(scanner.nextLine());
                }
                scanner.close();
            }
        } catch (Exception ignored) {

        }  finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result.toString();
    }

    List<FoodDetail> convertJSONToFood(String strJSON, String jsonKey)  {
        ArrayList<FoodDetail> foodDetails = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(strJSON);
            JSONArray recipes = jsonObject.optJSONArray(jsonKey);
            assert recipes != null;
            for (int i = 0; i < recipes.length(); i++) {
                FoodDetail food = new FoodDetail(recipes.optJSONObject(i));
                foodDetails.add(food);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodDetails;
    }
}
