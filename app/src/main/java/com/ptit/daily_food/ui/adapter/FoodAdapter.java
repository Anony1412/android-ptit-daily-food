package com.ptit.daily_food.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.daily_food.R;
import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.ui.interfaces.FoodItemClickInterface;
import com.ptit.daily_food.ultis.FoodType;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private int viewType;
    private FoodItemClickInterface foodItemClickInterface;
    private ArrayList<FoodDetail> foods = new ArrayList<>();

    public FoodAdapter(int viewType, FoodItemClickInterface foodItemClickInterface) {
        this.viewType = viewType;
        this.foodItemClickInterface = foodItemClickInterface;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case FoodType.DAILY_MENU: {
                return new DailyMenuViewHolder(
                        inflateView(parent.getContext(), R.layout.item_daily_menu_food, parent));
            }
            case FoodType.OTHER_LIST: {
                return new OtherFoodListViewHolder(
                        inflateView(parent.getContext(), R.layout.item_other_food_list, parent));
            }
            case FoodType.OTHER_GRID: {
                return new OtherFoodGridViewHolder(
                        inflateView(parent.getContext(), R.layout.item_other_food_grid, parent));
            }
            case FoodType.COOKING: {
                return new CookingFoodViewHolder(
                        inflateView(parent.getContext(), R.layout.item_food_collection, parent));
            }
            case FoodType.SEARCH_RESULT: {
                return new SearchResultFoodViewHolder(
                        inflateView(parent.getContext(), R.layout.item_search_result, parent));
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.bind(foods.get(position));
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    private View inflateView(Context context, int layoutRes, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(layoutRes, parent, false);
    }

    public void updateData(List<FoodDetail> newItems) {
        if (!foods.isEmpty()) foods.clear();
        foods.addAll(newItems);
        notifyDataSetChanged();
    }

    public void deleteItem(FoodDetail foodDetail) {
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getId().equals(foodDetail.getId())) {
                int index = foods.indexOf(foods.get(i));
                foods.remove(foods.get(i));
                notifyItemRemoved(index);
                return;
            }
        }
    }

    abstract class FoodViewHolder extends RecyclerView.ViewHolder {

        protected FoodDetail foodDetail;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foodItemClickInterface.onFoodItemClick(foodDetail);
                }
            });

        }

        abstract void bind(FoodDetail foodDetail);
    }

    class DailyMenuViewHolder extends FoodViewHolder {

        private TextView textFoodNameDailyMenu, textDailyMenuPrice, textDailyMenuReadyMinutes;
        private ImageView imageFoodDailyMenu;

        public DailyMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            textFoodNameDailyMenu = itemView.findViewById(R.id.textFoodNameDailyMenu);
            textDailyMenuPrice = itemView.findViewById(R.id.textDailyMenuPrice);
            textDailyMenuReadyMinutes = itemView.findViewById(R.id.textDailyMenuReadyMinutes);
            imageFoodDailyMenu = itemView.findViewById(R.id.imageFoodDailyMenu);
        }

        @Override
        void bind(FoodDetail foodDetail) {
            this.foodDetail = foodDetail;
            textFoodNameDailyMenu.setText(foodDetail.getTitle());
            textDailyMenuPrice.setText(foodDetail.getPrice());
            textDailyMenuReadyMinutes.setText(foodDetail.getReadyMinutes());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.transform(new CenterCrop(), new RoundedCorners(16));

            Glide.with(itemView.getContext())
                    .load(foodDetail.getImageUrl())
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_broken_image)
                    .into(imageFoodDailyMenu);
        }
    }

    class OtherFoodListViewHolder extends FoodViewHolder {

        private TextView textFoodNameOtherList, textOtherListPrice, textOtherListReadyMinutes;
        private ImageView imageFoodOtherList;

        public OtherFoodListViewHolder(@NonNull View itemView) {
            super(itemView);
            textFoodNameOtherList = itemView.findViewById(R.id.textFoodNameOtherList);
            textOtherListPrice = itemView.findViewById(R.id.textOtherListPrice);
            textOtherListReadyMinutes = itemView.findViewById(R.id.textOtherListReadyMinutes);
            imageFoodOtherList = itemView.findViewById(R.id.imageFoodOtherList);
        }

        @Override
        void bind(FoodDetail foodDetail) {
            this.foodDetail = foodDetail;
            textFoodNameOtherList.setText(foodDetail.getTitle());
            textOtherListPrice.setText(foodDetail.getPrice());
            textOtherListReadyMinutes.setText(foodDetail.getReadyMinutes());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.transform(new CenterCrop(), new RoundedCorners(16));

            Glide.with(itemView.getContext())
                    .load(foodDetail.getImageUrl())
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_broken_image)
                    .into(imageFoodOtherList);
        }
    }

    class OtherFoodGridViewHolder extends FoodViewHolder {

        private TextView textFoodOtherGrid, textOtherGridPrice, textOtherGridReadyMinutes;
        private ImageView imageFoodOtherGrid;

        public OtherFoodGridViewHolder(@NonNull View itemView) {
            super(itemView);
            textFoodOtherGrid = itemView.findViewById(R.id.textFoodOtherGrid);
            textOtherGridPrice = itemView.findViewById(R.id.textOtherGridPrice);
            textOtherGridReadyMinutes = itemView.findViewById(R.id.textOtherGridReadyMinutes);
            imageFoodOtherGrid = itemView.findViewById(R.id.imageFoodOtherGrid);
        }

        @Override
        void bind(FoodDetail foodDetail) {
            this.foodDetail = foodDetail;
            textFoodOtherGrid.setText(foodDetail.getTitle());
            textOtherGridPrice.setText(foodDetail.getPrice());
            textOtherGridReadyMinutes.setText(foodDetail.getReadyMinutes());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.transform(new CenterCrop(), new RoundedCorners(16));

            Glide.with(itemView.getContext())
                    .load(foodDetail.getImageUrl())
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_broken_image)
                    .into(imageFoodOtherGrid);
        }
    }

    class CookingFoodViewHolder extends FoodViewHolder {

        private TextView textFoodNameCollection, textFoodCollectionPrice, textFoodCollectionReadyMinutes;
        private ImageView imageFoodCollection;

        public CookingFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            textFoodNameCollection = itemView.findViewById(R.id.textFoodNameCollection);
            textFoodCollectionPrice = itemView.findViewById(R.id.textFoodCollectionPrice);
            textFoodCollectionReadyMinutes = itemView.findViewById(R.id.textFoodCollectionReadyMinutes);
            imageFoodCollection = itemView.findViewById(R.id.imageFoodCollection);
        }

        @Override
        void bind(FoodDetail foodDetail) {
            this.foodDetail = foodDetail;
            textFoodNameCollection.setText(foodDetail.getTitle());
            textFoodCollectionPrice.setText(foodDetail.getPrice());
            textFoodCollectionReadyMinutes.setText(foodDetail.getReadyMinutes());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.transform(new CenterCrop(), new RoundedCorners(16));

            Glide.with(itemView.getContext())
                    .load(foodDetail.getImageUrl())
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_broken_image)
                    .into(imageFoodCollection);
        }
    }

    class SearchResultFoodViewHolder extends FoodViewHolder {

        private TextView textFoodNameSearchResult;
        private ImageView imageFoodSearchResult;

        public SearchResultFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            textFoodNameSearchResult = itemView.findViewById(R.id.textFoodNameSearchResult);
            imageFoodSearchResult = itemView.findViewById(R.id.imageFoodSearchResult);
        }

        @Override
        void bind(FoodDetail foodDetail) {
            this.foodDetail = foodDetail;
            textFoodNameSearchResult.setText(foodDetail.getTitle());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.transform(new CenterCrop(), new RoundedCorners(16));

            Glide.with(itemView.getContext())
                    .load(foodDetail.getImageUrl())
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_broken_image)
                    .into(imageFoodSearchResult);
        }
    }
}
