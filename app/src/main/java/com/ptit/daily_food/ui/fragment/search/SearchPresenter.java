package com.ptit.daily_food.ui.fragment.search;

import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.repository.SearchRepository;
import com.ptit.daily_food.data.source.OnLoadedCallback;

import java.util.List;

public class SearchPresenter implements SearchContracts.Presenter {

    private SearchContracts.View view;
    private SearchRepository searchRepository;

    public SearchPresenter(SearchContracts.View view, SearchRepository searchRepository) {
        this.view = view;
        this.searchRepository = searchRepository;
    }

    @Override
    public void searchRecipeComplex(String keyword) {
        OnLoadedCallback<List<FoodDetail>> callback = new OnLoadedCallback<List<FoodDetail>>() {
            @Override
            public void onSuccess(List<FoodDetail> data) {
                view.showRecipeComplex(data);
            }

            @Override
            public void onFailure(Exception exception) {
                view.showError(exception);
            }
        };
        searchRepository.searchRecipeComplex(keyword, callback);
    }

    @Override
    public void searchRecipeById(String foodId) {
        OnLoadedCallback<List<FoodDetail>> callback = new OnLoadedCallback<List<FoodDetail>>() {
            @Override
            public void onSuccess(List<FoodDetail> data) {
                view.showRecipeById(data);
            }

            @Override
            public void onFailure(Exception exception) {
                view.showError(exception);
            }
        };
        searchRepository.searchRecipeById(foodId, callback);
    }
}
