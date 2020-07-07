package com.ptit.daily_food.ui.activity.splash;

import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.ptit.daily_food.R;
import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.repository.RecipeRepository;
import com.ptit.daily_food.data.source.local.RecipeLocalDataSource;
import com.ptit.daily_food.data.source.local.dao.FoodDao;
import com.ptit.daily_food.data.source.local.dao.FoodDaoImpl;
import com.ptit.daily_food.data.source.local.database.FoodDailyDatabase;
import com.ptit.daily_food.data.source.remote.RecipeRemoteDataSource;
import com.ptit.daily_food.ui.base.BaseActivity;
import com.ptit.daily_food.ui.activity.main.MainActivity;

import java.util.List;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    private ImageView imageLoadData;

    private SplashPresenter splashPresenter = null;

    @Override
    protected void initLayout() {
        this.layoutRes = R.layout.activity_splash;
    }

    @Override
    protected void initPresenter() {
        FoodDailyDatabase foodDailyDatabase =
                FoodDailyDatabase.getInstance(getApplicationContext());
        FoodDao foodDAO = FoodDaoImpl.getInstance(foodDailyDatabase);
        RecipeRepository recipeRepository = RecipeRepository.getInstance(
                RecipeRemoteDataSource.getInstance(),
                RecipeLocalDataSource.getInstance(foodDAO)
        );
        splashPresenter = new SplashPresenter(this, recipeRepository);
        splashPresenter.getRandomFoods();
    }

    @Override
    protected void initView() {
        imageLoadData = findViewById(R.id.imageLoadData);
        Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        imageLoadData.startAnimation(animRotate);
    }

    @Override
    public void onTransportDataToHome(List<FoodDetail> data) {
        Intent intentMain = MainActivity.getIntent(getApplicationContext(), data);
        startActivity(intentMain);
    }

    @Override
    public void showError(Exception exception) {
        Toast.makeText(
                this,
                getString(R.string.title_error) + exception.getMessage(),
                Toast.LENGTH_SHORT
        ).show();
        splashPresenter.getRandomFoods();
    }
}
