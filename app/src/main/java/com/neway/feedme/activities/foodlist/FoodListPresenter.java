package com.neway.feedme.activities.foodlist;

import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.neway.feedme.R;
import com.neway.feedme.adapters.FoodsViewHolder;
import com.neway.feedme.bases.BasePresenter;
import com.neway.feedme.interfaces.OnItemClickListener;
import com.neway.feedme.model.Category;
import com.neway.feedme.model.Food;
import com.squareup.picasso.Picasso;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class FoodListPresenter extends BasePresenter<FoodListContract.View> implements FoodListContract.Presenter {

    FirebaseDatabase database;
    DatabaseReference food;
    FirebaseRecyclerAdapter<Food, FoodsViewHolder> adapter = null;

    public FoodListPresenter() {
        database = FirebaseDatabase.getInstance();
        food = database.getReference("Foods");
    }

    @Override
    public void loadFoodsList(String menuKey) {
        getView().showLoading();
        adapter = new FirebaseRecyclerAdapter<Food, FoodsViewHolder>(
                Food.class, R.layout.food_item, FoodsViewHolder.class, food.orderByChild("MenuId").equalTo(menuKey)) {
            @Override
            protected void populateViewHolder(FoodsViewHolder viewHolder, Food model, int position) {
                viewHolder.food_text.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.food_image);

                final Food s = model;
                viewHolder.setItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        getView().onItemClicked(s, adapter.getRef(position).getKey());
                    }
                });

            }
        };

        getView().hideLoading();
        getView().onDataLoaded(adapter);
    }
}
