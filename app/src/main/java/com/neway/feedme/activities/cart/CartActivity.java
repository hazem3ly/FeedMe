package com.neway.feedme.activities.cart;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neway.feedme.R;
import com.neway.feedme.adapters.CartAdapter;
import com.neway.feedme.bases.BaseActivity;
import com.neway.feedme.model.Order;
import com.neway.feedme.widget.FButton;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class CartActivity extends BaseActivity implements CartContract.View {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView txtTotalPrice;
    FButton btnPlace;

    CartPresenter mPresenter;
    private List<Order> dataList;

    @Override
    protected int getContentResource() {
        return R.layout.activity_cart;
    }

    @Override
    protected void init(@Nullable Bundle state) {

        mPresenter = new CartPresenter();
        mPresenter.attach(this);

        recyclerView = findViewById(R.id.list_cart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btn_place_order);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

        mPresenter.getRequests();
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("One More Step!");
        builder.setMessage("Enter Your Address: ");

        final EditText editText = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(layoutParams);
        builder.setView(editText);
        builder.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.placeOrder(dataList, txtTotalPrice.getText().toString(),editText.getText().toString());
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();


    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void onDataLoaded(List<Order> dataList) {
        this.dataList = dataList;
        CartAdapter adapter = new CartAdapter(dataList);
        recyclerView.setAdapter(adapter);

        int total = 0;
        for (Order order : dataList)
            total += Integer.parseInt(order.getPrice()) * Integer.parseInt(order.getQuantity());

        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(format.format(total));
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
