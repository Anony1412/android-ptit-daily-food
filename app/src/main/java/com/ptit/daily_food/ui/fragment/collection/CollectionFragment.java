package com.ptit.daily_food.ui.fragment.collection;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.daily_food.R;
import com.ptit.daily_food.data.model.Collection;
import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.repository.RecipeRepository;
import com.ptit.daily_food.data.source.local.RecipeLocalDataSource;
import com.ptit.daily_food.data.source.local.dao.FoodDao;
import com.ptit.daily_food.data.source.local.dao.FoodDaoImpl;
import com.ptit.daily_food.data.source.local.database.FoodDailyDatabase;
import com.ptit.daily_food.data.source.remote.RecipeRemoteDataSource;
import com.ptit.daily_food.ui.activity.food.FoodDetailActivity;
import com.ptit.daily_food.ui.adapter.CollectionAdapter;
import com.ptit.daily_food.ui.base.BaseFragment;
import com.ptit.daily_food.ui.interfaces.FoodItemClickInterface;

import java.util.ArrayList;
import java.util.List;

public class CollectionFragment
        extends BaseFragment
        implements CollectionContracts.View,
        FoodItemClickInterface {

    private CollectionPresenter collectionPresenter;
    private ArrayList<Collection> collections = new ArrayList<>();
    private ArrayList<FoodDetail> favoriteFoods = new ArrayList<>();
    private ArrayList<FoodDetail> familyFoods = new ArrayList<>();
    private ArrayList<FoodDetail> partyFoods = new ArrayList<>();
    private TextView textActionBarTitle;
    private ImageButton buttonBackActionBar, buttonSearchActionBar, buttonCollectionActionBar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showFoodByCollections();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initLayout() {
        this.layoutRes = R.layout.fragment_collection;
    }

    @Override
    protected void initPresenter() {
        FoodDailyDatabase foodDailyDatabase = FoodDailyDatabase.getInstance(getContext());
        FoodDao foodDao = FoodDaoImpl.getInstance(foodDailyDatabase);
        RecipeRepository recipeRepository = RecipeRepository.getInstance(
                RecipeRemoteDataSource.getInstance(),
                RecipeLocalDataSource.getInstance(foodDao)
        );
        collectionPresenter = new CollectionPresenter(this, recipeRepository);
        collectionPresenter.getAllFavoriteFoods();
        collectionPresenter.getAllFamilyFoods();
        collectionPresenter.getAllPartyFoods();
    }

    @Override
    protected void initActionBar() {
        if (getActivity() != null) {
            textActionBarTitle = getActivity().findViewById(R.id.textActionBarTitle);
            buttonBackActionBar = getActivity().findViewById(R.id.buttonBackActionBar);
            buttonSearchActionBar = getActivity().findViewById(R.id.buttonSearchActionBar);
            buttonCollectionActionBar = getActivity().findViewById(R.id.buttonCollectionActionBar);
        }
        textActionBarTitle.setText(getString(R.string.title_collection));
        buttonBackActionBar.setVisibility(View.GONE);
        buttonSearchActionBar.setVisibility(View.GONE);
        buttonCollectionActionBar.setVisibility(View.GONE);
    }

    @Override
    protected void getArgument() {

    }

    @Override
    public void createFavoriteFoods(List<FoodDetail> favoriteFoods) {
        this.favoriteFoods.addAll(favoriteFoods);
    }

    @Override
    public void createFamilyFoods(List<FoodDetail> familyFoods) {
        this.familyFoods.addAll(familyFoods);
    }

    @Override
    public void createPartyFoods(List<FoodDetail> partyFoods) {
        this.partyFoods.addAll(partyFoods);
    }

    @Override
    public void showError(Exception exception) {
        Toast.makeText(
                getActivity(),
                getString(R.string.notification_update_data),
                Toast.LENGTH_SHORT
        ).show();
    }

    private RecyclerView recyclerViewCollection;

    private void showFoodByCollections() {
        if (getActivity() != null) {
            recyclerViewCollection = getActivity().findViewById(R.id.recyclerViewCollection);
        }
        initCollection();
        recyclerViewCollection.setAdapter(new CollectionAdapter(collections));
    }

    @Override
    public void onFoodItemClick(FoodDetail foodDetail) {
        if (getActivity() != null) {
            startActivityForResult(
                    FoodDetailActivity.getIntent(getActivity(), foodDetail),
                    FoodDetailActivity.REQUEST_FOOD_DETAIL_ACTIVITY
            );
        }
    }

    private void initCollection() {
        collections.add(
                new Collection(
                        getString(R.string.title_favorite),
                        R.drawable.ic_favorite,
                        false,
                        favoriteFoods
                )
        );
        collections.add(
                new Collection(
                        getString(R.string.title_family),
                        R.drawable.ic_family,
                        false,
                        familyFoods
                )
        );
        collections.add(
                new Collection(
                        getString(R.string.title_favorite),
                        R.drawable.ic_favorite,
                        false,
                        favoriteFoods
                )
        );
        collections.add(
                new Collection(
                        getString(R.string.title_party),
                        R.drawable.ic_party,
                        false,
                        partyFoods
                )
        );
    }

    public static CollectionFragment newInstance() {
        return new CollectionFragment();
    }
}
