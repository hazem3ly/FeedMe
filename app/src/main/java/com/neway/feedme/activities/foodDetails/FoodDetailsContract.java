package com.neway.feedme.activities.foodDetails;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.neway.feedme.bases.BaseMvpPresenter;
import com.neway.feedme.bases.BaseView;
import com.neway.feedme.model.Food;
import com.neway.feedme.model.Order;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class FoodDetailsContract {


    // User actions. Presenter will implement
    interface Presenter extends BaseMvpPresenter<FoodDetailsContract.View> {
        void addToCart(Order order);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View extends BaseView {

    }

}
