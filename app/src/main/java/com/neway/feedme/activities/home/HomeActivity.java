package com.neway.feedme.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.neway.feedme.R;
import com.neway.feedme.activities.cart.CartActivity;
import com.neway.feedme.activities.foodlist.FoodListActivity;
import com.neway.feedme.activities.launcher.LauncherActivity;
import com.neway.feedme.activities.orderStatus.OrderStatusActivity;
import com.neway.feedme.bases.BaseActivity;
import com.neway.feedme.model.App;
import com.neway.feedme.model.Category;
import com.neway.feedme.model.Navegator;
import com.neway.feedme.services.StatusChangeListener;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class HomeActivity extends BaseActivity implements HomeContract.View, NavigationView.OnNavigationItemSelectedListener {

    HomePresenter mPresenter;

    RecyclerView menu_recycler;
    LinearLayoutManager layoutManager;
    FloatingActionButton fab;

    @Override
    protected int getContentResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void init(@Nullable Bundle state) {

        startService(new Intent(this, StatusChangeListener.class));

        mPresenter = new HomePresenter();
        mPresenter.attach(this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });
        menu_recycler = findViewById(R.id.recycler_menu);
        menu_recycler.setLayoutManager(layoutManager);

        initialDrawer();
        mPresenter.loadCategories();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void initialDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initialHeader(navigationView);
    }

    private void initialHeader(NavigationView navigationView) {
        View view = navigationView.getHeaderView(0);
        TextView user = view.findViewById(R.id.user_name);
        user.setText(String.valueOf(App.getCurrentUser().getName()));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_cart:
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
                break;
            case R.id.nav_log_out:
                startActivity(new Intent(HomeActivity.this, LauncherActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            case R.id.nav_menu:
                break;
            case R.id.nav_orders:
                startActivity(new Intent(HomeActivity.this, OrderStatusActivity.class));
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDataLoaded(FirebaseRecyclerAdapter adapter) {
        menu_recycler.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(String menuKey, Category model) {
        Bundle data = new Bundle();
        data.putString("KEY", menuKey);
        Navegator.navigateToActivity(this, FoodListActivity.class, data);
        Toast.makeText(this, model.getName(), Toast.LENGTH_SHORT).show();
    }
}
