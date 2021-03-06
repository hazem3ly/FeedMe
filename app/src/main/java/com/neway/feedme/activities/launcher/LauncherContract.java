package com.neway.feedme.activities.launcher;

import com.neway.feedme.bases.BaseMvpPresenter;
import com.neway.feedme.bases.BaseView;
import com.neway.feedme.model.User;

/**
 * Created by Hazem Ali
 * On 5/5/2018.
 */
public class LauncherContract {

    // User actions. Presenter will implement
    interface Presenter extends BaseMvpPresenter<LauncherContract.View> {
        void performSignIn();

        void performSignUp();

        void backToLauncher();

        void doLogin(String phone, String password);

        void doSignUp(String phone,String name,String password);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View extends BaseView {
        void onSignInCallback(boolean successful, User user);

        void onSignUpCallback(boolean successful, String error);

        void switchToLogin();

        void switchToSignUp();

        void switchToLauncher();
    }

}
