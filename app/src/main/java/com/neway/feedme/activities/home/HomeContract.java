package com.neway.feedme.activities.home;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.neway.feedme.bases.BaseMvpPresenter;
import com.neway.feedme.bases.BaseView;
import com.neway.feedme.model.Category;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class HomeContract {


    // User actions. Presenter will implement
    interface Presenter extends BaseMvpPresenter<HomeContract.View> {
        void loadCategories();
    }

    // Action callbacks. Activity/Fragment will implement
    interface View extends BaseView {
        void onDataLoaded(FirebaseRecyclerAdapter adapter);

        void onItemClicked(String menuKey, Category model);
    }

}
