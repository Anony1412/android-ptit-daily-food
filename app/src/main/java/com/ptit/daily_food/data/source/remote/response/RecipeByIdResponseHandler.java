package com.ptit.daily_food.data.source.remote.response;

import com.ptit.daily_food.data.model.FoodDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecipeByIdResponseHandler implements DataResponseHandler {

    private static final String METHOD_GET = "GET";

    @Override
    public List<FoodDetail> getResponse(String urlRequest) {
        String strJson = "";
        try {
            strJson = handleConnect(new URL(urlRequest));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return convertJsonToFood(strJson);
    }

    private List<FoodDetail> convertJsonToFood(String strJson) {
        ArrayList<FoodDetail> foodDetails = new ArrayList<>();
        try {
            JSONObject recipe = new JSONObject(strJson);
            FoodDetail foodDetail = new FoodDetail(recipe);
            foodDetails.add(foodDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodDetails;
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
}
