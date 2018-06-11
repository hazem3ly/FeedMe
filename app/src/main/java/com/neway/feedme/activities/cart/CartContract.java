package com.neway.feedme.activities.cart;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.neway.feedme.bases.BaseMvpPresenter;
import com.neway.feedme.bases.BaseView;
import com.neway.feedme.model.Order;

import java.util.List;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class CartContract {


    // User actions. Presenter will implement
    interface Presenter extends BaseMvpPresenter<CartContract.View> {
        void getRequests();
        void placeOrder(List<Order> orders, String total,String address);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View extends BaseView {
        void onDataLoaded(List<Order> dataList);
        void finishActivity();
    }

}
