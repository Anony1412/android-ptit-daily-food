package com.ptit.daily_food.ui.activity.food;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.text.HtmlCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.ptit.daily_food.R;
import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.data.repository.RecipeRepository;
import com.ptit.daily_food.data.source.local.RecipeLocalDataSource;
import com.ptit.daily_food.data.source.local.dao.FoodDao;
import com.ptit.daily_food.data.source.local.dao.FoodDaoImpl;
import com.ptit.daily_food.data.source.local.database.FoodDailyDatabase;
import com.ptit.daily_food.data.source.remote.RecipeRemoteDataSource;
import com.ptit.daily_food.ui.adapter.InstructionAdapter;
import com.ptit.daily_food.ui.base.BaseActivity;
import com.ptit.daily_food.ui.fragment.instruction.InstructionFragment;

import java.util.ArrayList;

public class FoodDetailActivity
        extends BaseActivity
        implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final int REQUEST_FOOD_DETAIL_ACTIVITY = 0;
    public static final String RESULT_FOOD_ID = "result_food_id";
    private static final String EXTRA_FOOD_ITEM =
            "com.sunasterisk.fooddaily.utils.Constants.EXTRA_FOOD_ITEM";
    private TextView textActionBarTitle;
    private ImageButton buttonSearchActionBar;
    private TextView textFoodNameDes, textSummaryDes;
    private TextView textReadyMinutesDes, textPriceDes, textIngredientDes;
    private ImageView imageFoodDetail;
    private ImageButton buttonCollectionActionBar, buttonBackActionBar;
    private Button buttonStart, buttonNoThank, buttonShareOnFacebook;
    private Button buttonFavorite, buttonFamily, buttonParty;
    private ImageButton buttonCloseChooseDialog;
    private ViewPager pagerInstruction;

    private FoodDetailPresenter foodDetailPresenter;
    private FoodDetail food;
    private Boolean isCompletedCooking = false;
    private int currentPositionPage = 0;
    private InstructionAdapter instructionAdapter;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private ConstraintLayout frameFoodDetailPreview, frameFoodDetailInstruction;
    private ImageButton buttonPreviousNavigation, buttonNextNavigation;
    private Button buttonCompleted;

    public static Intent getIntent(Context context, FoodDetail food) {
        Intent intent = new Intent(context, FoodDetailActivity.class);
        intent.putExtra(EXTRA_FOOD_ITEM, food);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonBackActionBar: {
                backToHomeScreen();
                break;
            }
            case R.id.buttonCollectionActionBar: {
                showDialogChooseCollection();
                break;
            }
            case R.id.buttonStart: {
                controlStartCooking();
                break;
            }
            case R.id.buttonCompleted: {
                controlCompletedCooking();
                break;
            }
            case R.id.buttonPreviousNavigation: {
                goToPreviousPage();
                break;
            }
            case R.id.buttonNextNavigation: {
                goToNextPage();
                break;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPositionPage = position;
        if (position == 0) {
            hideButtonPrevious();
            displayButtonNext();
        } else if (position == pagerInstruction.getAdapter().getCount() - 1) {
            displayButtonCompleted();
            hideButtonNext();
            displayButtonPrevious();
        } else {
            displayButtonNav();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void goToNextPage() {
        if (pagerInstruction.getAdapter() != null) {
            if (currentPositionPage + 1 < pagerInstruction.getAdapter().getCount()) {
                pagerInstruction.setCurrentItem(currentPositionPage + 1);
            }
        }
    }

    private void goToPreviousPage() {
        if (pagerInstruction.getAdapter() != null) {
            if (currentPositionPage - 1 >= 0) {
                pagerInstruction.setCurrentItem(currentPositionPage - 1);
            }
        }
    }

    private void controlCompletedCooking() {
        if (food != null) {
            foodDetailPresenter.deleteFoodFromCooking(food);
        }
        isCompletedCooking = true;
        showDialogCongratulations();
    }

    private void showDialogCongratulations() {
        buttonNoThank = findViewById(R.id.buttonNoThank);
        buttonShareOnFacebook = findViewById(R.id.buttonShareOnFacebook);
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_cogratulations);
        dialog.show();
        buttonNoThank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonShareOnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareRecipeOnFacebook();
                dialog.dismiss();
            }
        });
    }

    private void shareRecipeOnFacebook() {
        BitmapDrawable drawable = (BitmapDrawable) imageFoodDetail.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();
        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .setShareHashtag(
                        new ShareHashtag.Builder()
                                .setHashtag("#food_detail")
                                .build()
                )
                .build();
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            shareDialog.show(sharePhotoContent);
        }
    }

    private void showDialogChooseCollection() {
        buttonCloseChooseDialog = findViewById(R.id.buttonCloseChooseDialog);
        buttonFavorite = findViewById(R.id.buttonFavorite);
        buttonFamily = findViewById(R.id.buttonFamily);
        buttonParty = findViewById(R.id.buttonParty);

        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_choose_collection);
        dialog.show();
        buttonCloseChooseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food != null) {
                    addFoodToFavorite(food);
                    dialog.dismiss();
                }
            }
        });
        buttonFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food != null) {
                    addFoodToFamily(food);
                    dialog.dismiss();
                }
            }
        });
        buttonParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food != null) {
                    addFoodToParty(food);
                    dialog.dismiss();
                }
            }
        });
    }

    private void backToHomeScreen() {
        if (isCompletedCooking) {
            setResult(
                    Activity.RESULT_OK,
                    new Intent().putExtra(RESULT_FOOD_ID, food)
            );
        }
        finish();
    }

    private void controlStartCooking() {
        showFrameInstruction();
        if (food != null) {
            foodDetailPresenter.addFoodToCooking(food);
        }
    }

    private void showFrameInstruction() {
        frameFoodDetailPreview = findViewById(R.id.frameFoodDetailPreview);
        frameFoodDetailInstruction = findViewById(R.id.frameFoodDetailInstruction);
        buttonPreviousNavigation = findViewById(R.id.buttonPreviousNavigation);
        buttonNextNavigation = findViewById(R.id.buttonNextNavigation);

        buttonStart.setVisibility(View.GONE);
        frameFoodDetailPreview.setVisibility(View.GONE);
        frameFoodDetailInstruction.setVisibility(View.VISIBLE);
        initInstructionAdapter();
        buttonPreviousNavigation.setOnClickListener(this);
        buttonNextNavigation.setOnClickListener(this);
    }

    private void initInstructionAdapter() {

        ArrayList fragmentSteps = new ArrayList();
        for (int i = 0; i < food.getInstructions().size(); i++) {
            fragmentSteps.add(InstructionFragment.newInstance(food.getInstructions().get(i)));
        }
        instructionAdapter = new InstructionAdapter(getSupportFragmentManager());
        instructionAdapter.addFragment(fragmentSteps);

        pagerInstruction.setAdapter(instructionAdapter);
        pagerInstruction.addOnPageChangeListener(this);
    }

    private void addFoodToParty(FoodDetail food) {
        foodDetailPresenter.addFoodToParty(food);
    }

    private void addFoodToFamily(FoodDetail food) {
        foodDetailPresenter.addFoodToFamily(food);
    }

    private void addFoodToFavorite(FoodDetail food) {
        foodDetailPresenter.addFoodToFavorite(food);
    }

    private void displayButtonNav() {
        buttonCompleted = findViewById(R.id.buttonCompleted);

        buttonCompleted.setVisibility(View.GONE);
        buttonPreviousNavigation.setVisibility(View.VISIBLE);
        buttonNextNavigation.setVisibility(View.VISIBLE);
    }

    private void hideButtonNext() {
        buttonNextNavigation.setVisibility(View.GONE);
    }

    private void hideButtonPrevious() {
        buttonPreviousNavigation.setVisibility(View.GONE);
    }

    private void displayButtonNext() {
        buttonNextNavigation.setVisibility(View.VISIBLE);
    }

    private void displayButtonPrevious() {
        buttonPreviousNavigation.setVisibility(View.VISIBLE);
    }

    private void displayButtonCompleted() {
        buttonCompleted.setVisibility(View.VISIBLE);
        buttonCompleted.setOnClickListener(this);
    }

    @Override
    protected void initLayout() {
        this.layoutRes = R.layout.activity_food_detail;
    }

    @Override
    protected void initPresenter() {
        FoodDailyDatabase foodDailyDatabase = FoodDailyDatabase.getInstance(getApplicationContext());
        FoodDao foodDAO = FoodDaoImpl.getInstance(foodDailyDatabase);
        RecipeRepository recipeRepository = RecipeRepository.getInstance(
                RecipeRemoteDataSource.getInstance(),
                RecipeLocalDataSource.getInstance(foodDAO)
        );
        foodDetailPresenter = new FoodDetailPresenter(recipeRepository);
    }

    @Override
    protected void initView() {
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        food = getIntent().getParcelableExtra(EXTRA_FOOD_ITEM);
        if (food != null) {
            showFoodDetail(food);
        }
        initActionBar();
        initListener();
    }

    private void initListener() {
        buttonCollectionActionBar = findViewById(R.id.buttonCollectionActionBar);
        buttonBackActionBar = findViewById(R.id.buttonBackActionBar);
        buttonStart = findViewById(R.id.buttonStart);

        buttonCollectionActionBar.setOnClickListener(this);
        buttonBackActionBar.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
    }

    private void initActionBar() {
        textActionBarTitle = findViewById(R.id.textActionBarTitle);
        buttonSearchActionBar = findViewById(R.id.buttonSearchActionBar);
        buttonSearchActionBar.setVisibility(View.GONE);
        textActionBarTitle.setText(getString(R.string.title_food_detail));
        pagerInstruction = findViewById(R.id.pagerInstruction);
    }

    private void showFoodDetail(FoodDetail food) {
        textFoodNameDes = findViewById(R.id.textFoodNameDes);
        textSummaryDes = findViewById(R.id.textFoodNameDes);
        textReadyMinutesDes = findViewById(R.id.textReadyMinutesDes);
        textPriceDes = findViewById(R.id.textPriceDes);
        textIngredientDes = findViewById(R.id.textIngredientDes);
        imageFoodDetail = findViewById(R.id.imageFoodDetail);

        textFoodNameDes.setText(food.getTitle());
        textFoodNameDes.setSelected(true);
        textSummaryDes.setText(HtmlCompat.fromHtml(
                food.getSummary(),
                HtmlCompat.FROM_HTML_MODE_COMPACT
        ));
        textReadyMinutesDes.setText(food.getRequiredTime());
        textPriceDes.setText(food.getPriceEstimate());
        if (food.getIngredient() != null) {
            textIngredientDes.setText(food.getIngredient());
        } else {
            textIngredientDes.setText(getString(R.string.title_updating));
        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new CenterCrop(), new RoundedCorners(16));

        Glide.with(this)
                .load(food.getImageUrl())
                .apply(requestOptions)
                .placeholder(R.drawable.ic_broken_image)
                .into(imageFoodDetail);
    }
}
