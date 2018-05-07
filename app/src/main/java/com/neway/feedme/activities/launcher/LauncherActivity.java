package com.neway.feedme.activities.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.neway.feedme.activities.home.HomeActivity;
import com.neway.feedme.model.Navegator;
import com.neway.feedme.R;
import com.neway.feedme.bases.BaseActivity;
import com.neway.feedme.model.App;
import com.neway.feedme.model.User;
import com.neway.feedme.widget.FButton;
import com.rengwuxian.materialedittext.MaterialEditText;


/**
 * Created by Hazem Ali
 * On 5/5/2018.
 */
public class LauncherActivity extends BaseActivity implements LauncherContract.View {

    FButton signInBtn, signUpBtn, doSignBtn, doSignUp;
    LauncherPresenter mPresenter;

    View loginView, SignUpView, launcherView;

    ProgressBar progress_bar;

    MaterialEditText loginPasswordEt, loginPhoneEt, signUpPasswordEt, signUpNameEt, signUpPhoneEt;

    @Override
    protected int getContentResource() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void init(@Nullable Bundle state) {

        mPresenter = new LauncherPresenter();
        mPresenter.attach(this);


        progress_bar = findViewById(R.id.progress_bar);
        loginView = findViewById(R.id.login_screen);
        SignUpView = findViewById(R.id.sign_up_screen);
        launcherView = findViewById(R.id.launcher_screen);

        loginPasswordEt = findViewById(R.id.sign_in_password_et);
        loginPhoneEt = findViewById(R.id.sign_in_phone_et);
        signUpPasswordEt = findViewById(R.id.sign_up_password_et);
        signUpNameEt = findViewById(R.id.sign_up_name_et);
        signUpPhoneEt = findViewById(R.id.sign_up_phone_et);


        doSignBtn = findViewById(R.id.do_login);
        doSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginPhoneEt.getText().toString().isEmpty() && !loginPasswordEt.getText().toString().isEmpty())
                    mPresenter.doLogin(loginPhoneEt.getText().toString(), loginPasswordEt.getText().toString());
                else
                    Toast.makeText(LauncherActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
            }
        });

        doSignUp = findViewById(R.id.doSignUp);
        doSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!signUpPhoneEt.getText().toString().isEmpty() &&
                        !signUpNameEt.getText().toString().isEmpty() && !signUpPasswordEt.getText().toString().isEmpty())
                    mPresenter.doSignUp(signUpPhoneEt.getText().toString(),
                            signUpNameEt.getText().toString(), signUpPasswordEt.getText().toString());
                else
                    Toast.makeText(LauncherActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
            }
        });

        signInBtn = findViewById(R.id.login_btn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.performSignIn();
            }
        });
        signUpBtn = findViewById(R.id.sign_up_btn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.performSignUp();
            }
        });
    }

    @Override
    public void onSignInCallback(boolean successful, User user) {
        if (successful) {
            App.setCurrentUser(user);
            Navegator.navigateToActivity(this, HomeActivity.class);
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Login Error ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSignUpCallback(boolean successful, String error) {
        if (successful) {
            mPresenter.backToLauncher();
            Toast.makeText(this, "SignUp Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "SignUp Error " + error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void switchToLogin() {
        loginView.setVisibility(View.VISIBLE);
        SignUpView.setVisibility(View.GONE);
        launcherView.setVisibility(View.GONE);
    }

    @Override
    public void switchToSignUp() {
        loginView.setVisibility(View.GONE);
        SignUpView.setVisibility(View.VISIBLE);
        launcherView.setVisibility(View.GONE);
    }

    @Override
    public void switchToLauncher() {
        loginView.setVisibility(View.GONE);
        SignUpView.setVisibility(View.GONE);
        launcherView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (launcherView.getVisibility() == View.GONE)
            mPresenter.backToLauncher();
        else
            super.onBackPressed();
    }

    @Override
    public void showLoading() {
        progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progress_bar.setVisibility(View.GONE);
    }
}
