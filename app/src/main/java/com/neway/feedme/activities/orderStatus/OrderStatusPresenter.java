package com.neway.feedme.activities.orderStatus;

import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.neway.feedme.R;
import com.neway.feedme.adapters.OrderViewHolder;
import com.neway.feedme.bases.BasePresenter;
import com.neway.feedme.database.Database;
import com.neway.feedme.model.App;
import com.neway.feedme.model.Order;
import com.neway.feedme.model.Request;
import com.neway.feedme.model.User;

import java.util.List;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class OrderStatusPresenter extends BasePresenter<OrderStatusContract.View> implements OrderStatusContract.Presenter {
    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    public OrderStatusPresenter() {
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
    }

    @Override
    public void getOrders(String phone) {
        getView().showLoading();

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout_item,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(covertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.order_address.setText(model.getAddress());
            }
        };

        getView().hideLoading();
        getView().onDataLoaded(adapter);
    }

    private String covertCodeToStatus(String status) {
        switch (status) {
            case "0":
                return "Placed";
            case "1":
                return "On It's Way";
            default:
                return "Shipped";
        }
    }


}
