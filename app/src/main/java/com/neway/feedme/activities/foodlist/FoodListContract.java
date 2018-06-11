package com.neway.feedme.activities.foodlist;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.neway.feedme.bases.BaseMvpPresenter;
import com.neway.feedme.bases.BaseView;
import com.neway.feedme.model.Category;
import com.neway.feedme.model.Food;

import java.util.List;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class FoodListContract {


    // User actions. Presenter will implement
    interface Presenter extends BaseMvpPresenter<FoodListContract.View> {
        void loadFoodsList(String menuKey);
        void loadSuggest(String menuKey);
        void searchFood(String searchString);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View extends BaseView {
        void onDataLoaded(FirebaseRecyclerAdapter adapter);
        void onItemClicked(Food model,String foodId);
        void setSuggestList(List<String> suggestList);
    }

}
