package com.neway.feedme.activities.orderStatus;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.neway.feedme.bases.BaseMvpPresenter;
import com.neway.feedme.bases.BaseView;
import com.neway.feedme.model.Order;

import java.util.List;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class OrderStatusContract {


    // User actions. Presenter will implement
    interface Presenter extends BaseMvpPresenter<OrderStatusContract.View> {
        void getOrders();
    }

    // Action callbacks. Activity/Fragment will implement
    interface View extends BaseView {
        void onDataLoaded(FirebaseRecyclerAdapter adapter);
        void finishActivity();
    }

}
