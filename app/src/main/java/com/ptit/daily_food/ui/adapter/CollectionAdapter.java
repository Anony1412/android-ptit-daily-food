package com.ptit.daily_food.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.daily_food.R;
import com.ptit.daily_food.data.model.Collection;
import com.ptit.daily_food.data.model.FoodDetail;
import com.ptit.daily_food.ui.interfaces.FoodItemClickInterface;
import com.ptit.daily_food.ultis.FoodType;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {

    private List<Collection> collections;

    public CollectionAdapter(List<Collection> collections) {
        this.collections = collections;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CollectionViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_collection, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
        holder.bind(collections.get(position));
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    class CollectionViewHolder extends RecyclerView.ViewHolder implements FoodItemClickInterface {

        private FoodAdapter adapterContent =
                new FoodAdapter(FoodType.OTHER_LIST, this);

        private Collection collection;

        private TextView textCollectionName;
        private ImageView imageCollection;
        private RecyclerView recyclerViewFoodOfCollection;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            textCollectionName = itemView.findViewById(R.id.textCollectionName);
            imageCollection = itemView.findViewById(R.id.imageCollection);
            recyclerViewFoodOfCollection = itemView.findViewById(R.id.recyclerViewFoodOfCollection);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collection.setExpanded(!collection.getExpanded());
                }
            });

            recyclerViewFoodOfCollection.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            recyclerViewFoodOfCollection.setAdapter(adapterContent);
        }

        @Override
        public void onFoodItemClick(FoodDetail foodDetail) {

        }

        void bind(Collection collection) {
            this.collection = collection;
            textCollectionName.setText(collection.getName());
            imageCollection.setImageResource(collection.getImageRes());
            if (collection.getExpanded()) {
                recyclerViewFoodOfCollection.setVisibility(View.VISIBLE);
            } else {
                recyclerViewFoodOfCollection.setVisibility(View.GONE);
            }
            adapterContent.updateData(collection.getContent());
        }
    }
}
