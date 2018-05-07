package com.neway.feedme.activities.foodlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.neway.feedme.R;
import com.neway.feedme.bases.BaseActivity;
import com.neway.feedme.model.Category;
import com.neway.feedme.model.Food;
import com.neway.feedme.model.Navegator;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class FoodListActivity extends BaseActivity implements FoodListContract.View {

    FoodListPresenter mPresenter;

    RecyclerView foods_recycler;
    LinearLayoutManager layoutManager;

    String menuKey = null;

    @Override
    protected int getContentResource() {
        return R.layout.activity_foods;
    }

    @Override
    protected void init(@Nullable Bundle state) {

        if (!getKey()) {
            finish();
        }

        mPresenter = new FoodListPresenter();
        mPresenter.attach(this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        foods_recycler = findViewById(R.id.foods_list);
        foods_recycler.setHasFixedSize(true);
        foods_recycler.setLayoutManager(layoutManager);

        mPresenter.loadFoodsList(menuKey);
    }

    private boolean getKey() {
        if (getIntent().hasExtra(Navegator.BUNDLE_DATA)) {
            Bundle data = getIntent().getBundleExtra(Navegator.BUNDLE_DATA);
            if (data != null && data.containsKey("KEY")) {
                menuKey = data.getString("KEY");
                return menuKey != null;
            }
        }
        return false;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void onDataLoaded(FirebaseRecyclerAdapter adapter) {
        foods_recycler.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(Food model) {
        Toast.makeText(this, model.getName(), Toast.LENGTH_SHORT).show();
    }
}
