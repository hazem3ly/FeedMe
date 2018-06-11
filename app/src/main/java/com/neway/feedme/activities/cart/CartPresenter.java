package com.neway.feedme.activities.cart;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.neway.feedme.bases.BasePresenter;
import com.neway.feedme.database.Database;
import com.neway.feedme.model.App;
import com.neway.feedme.model.Order;
import com.neway.feedme.model.Request;
import com.neway.feedme.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class CartPresenter extends BasePresenter<CartContract.View> implements CartContract.Presenter {
    FirebaseDatabase database;
    DatabaseReference requests;


    public CartPresenter() {
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
    }

    @Override
    public void getRequests() {
        getView().showLoading();
        List<Order> orders = new Database(App.getInstance()).getCarts();
        getView().hideLoading();
        getView().onDataLoaded(orders);
    }

    @Override
    public void placeOrder(List<Order> orders, String total, String address) {
        User user = App.getCurrentUser();
        Request request = new Request(user.getPhone(), user.getName(), address, total, orders);

        requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
        new Database(App.getInstance()).cleanChart();
        getView().finishActivity();
        Toast.makeText(App.getInstance(), "Thank You! Order Placed", Toast.LENGTH_SHORT).show();
    }
}
