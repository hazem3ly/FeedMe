package com.neway.feedme.activities.foodDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.neway.feedme.R;
import com.neway.feedme.bases.BaseActivity;
import com.neway.feedme.model.Food;
import com.neway.feedme.model.Navegator;
import com.neway.feedme.model.Order;
import com.squareup.picasso.Picasso;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class FoodDetailsActivity extends BaseActivity implements FoodDetailsContract.View {

    public static Food SELECTED_FOOD = null;
    public static String FOOD_ID = "";
    FoodDetailsPresenter mPresenter;

    TextView foodName, foodPrize, foodDescription;
    ImageView foodImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;


    @Override
    protected int getContentResource() {
        return R.layout.activity_food_details;
    }

    @Override
    protected void init(@Nullable Bundle state) {

        foodName = findViewById(R.id.food_name);
        foodPrize = findViewById(R.id.food_price);
        foodDescription = findViewById(R.id.food_description);
        foodImage = findViewById(R.id.img_food);
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        btnCart = findViewById(R.id.btnCart);
        numberButton = findViewById(R.id.number_button);


        if (SELECTED_FOOD == null) {
            finish();
        }

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsingAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        Picasso.get().load(SELECTED_FOOD.getImage()).into(foodImage);
        collapsingToolbarLayout.setTitle(SELECTED_FOOD.getName());
        foodPrize.setText(SELECTED_FOOD.getPrice());
        foodDescription.setText(SELECTED_FOOD.getDescription());
        foodName.setText(SELECTED_FOOD.getName());

        mPresenter = new FoodDetailsPresenter();
        mPresenter.attach(this);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order(FOOD_ID, SELECTED_FOOD.getName(), numberButton.getNumber(),
                        SELECTED_FOOD.getPrice(), SELECTED_FOOD.getDiscount());
                mPresenter.addToCart(order);
                Toast.makeText(FoodDetailsActivity.this, "Added To CartAdapter", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


}
