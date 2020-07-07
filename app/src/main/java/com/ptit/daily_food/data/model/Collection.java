package com.ptit.daily_food.data.model;

import java.util.List;

public class Collection {

    private String name;
    private int imageRes;
    private Boolean isExpanded;
    private List<FoodDetail> content;

    public Collection(String name, int imageRes, Boolean isExpanded, List<FoodDetail> content) {
        this.name = name;
        this.imageRes = imageRes;
        this.isExpanded = isExpanded;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }

    public List<FoodDetail> getContent() {
        return content;
    }

    public void setContent(List<FoodDetail> content) {
        this.content = content;
    }
}
