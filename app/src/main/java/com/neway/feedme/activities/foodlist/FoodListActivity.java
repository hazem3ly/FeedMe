package com.neway.feedme.activities.foodlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.neway.feedme.R;
import com.neway.feedme.activities.foodDetails.FoodDetailsActivity;
import com.neway.feedme.bases.BaseActivity;
import com.neway.feedme.model.Category;
import com.neway.feedme.model.Food;
import com.neway.feedme.model.Navegator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class FoodListActivity extends BaseActivity implements FoodListContract.View {

    FoodListPresenter mPresenter;

    RecyclerView foods_recycler;
    LinearLayoutManager layoutManager;

    String menuKey = null;

    MaterialSearchBar materialSearchBar;
    private List<String> suggestList;

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

        materialSearchBar = findViewById(R.id.search_bar);
        materialSearchBar.setHint("Enter You Food!");
        mPresenter.loadSuggest(menuKey);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String search : suggestList){
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
             }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) mPresenter.loadFoodsList(menuKey);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                mPresenter.searchFood(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        mPresenter.loadFoodsList(menuKey);
    }

    @Override
    public void setSuggestList(List<String> suggestList) {
        this.suggestList = suggestList;
        materialSearchBar.setLastSuggestions(suggestList);
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
    public void onItemClicked(Food model, String foodID) {

        FoodDetailsActivity.SELECTED_FOOD = model;
        FoodDetailsActivity.FOOD_ID = foodID;
        Navegator.navigateToActivity(this, FoodDetailsActivity.class);
        Toast.makeText(this, model.getName(), Toast.LENGTH_SHORT).show();
    }


}
