package com.neway.feedme.activities.foodDetails;

import android.content.Context;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.neway.feedme.R;
import com.neway.feedme.adapters.FoodsViewHolder;
import com.neway.feedme.bases.BasePresenter;
import com.neway.feedme.database.Database;
import com.neway.feedme.interfaces.OnItemClickListener;
import com.neway.feedme.model.App;
import com.neway.feedme.model.Food;
import com.neway.feedme.model.Order;
import com.squareup.picasso.Picasso;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class FoodDetailsPresenter extends BasePresenter<FoodDetailsContract.View> implements FoodDetailsContract.Presenter {



    public FoodDetailsPresenter() {

    }

    @Override
    public void addToCart(Order order) {

        new Database(App.getInstance()).addToChart(order);

    }
}
