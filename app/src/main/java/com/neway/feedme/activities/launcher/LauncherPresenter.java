package com.neway.feedme.activities.launcher;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.neway.feedme.bases.BasePresenter;
import com.neway.feedme.model.User;

/**
 * Created by Hazem Ali
 * On 5/5/2018.
 */
public class LauncherPresenter extends BasePresenter<LauncherContract.View> implements LauncherContract.Presenter {

    private final FirebaseDatabase database;
    private final DatabaseReference tableUser;

    public LauncherPresenter() {
        database = FirebaseDatabase.getInstance();
        tableUser = database.getReference("user");
    }

    @Override
    public void performSignIn() {
        getView().switchToLogin();
    }

    @Override
    public void performSignUp() {
        getView().switchToSignUp();
    }

    @Override
    public void backToLauncher() {
        getView().switchToLauncher();
    }

    @Override
    public void doLogin(String phone, String password) {
        getView().showLoading();
        login(phone, password);
    }

    @Override
    public void doSignUp(String phone, String name, String password) {
        getView().showLoading();
        signUp(phone, name, password);
    }

    private void signUp(final String phone, final String name, final String password) {

        tableUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(phone).exists()) {
                    User user = new User(name, password, phone);
                    tableUser.child(phone).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                getView().hideLoading();
                                getView().onSignUpCallback(true, "");
                            } else {
                                getView().hideLoading();
                                getView().onSignUpCallback(false, "Error Signing Up ");
                            }
                        }
                    });
                } else {
                    getView().hideLoading();
                    getView().onSignUpCallback(false, "Phone Number Already Exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().hideLoading();
                getView().onSignUpCallback(false, "Sign up cancelled");
            }
        });
    }

    private void login(final String phone, final String password) {
        tableUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()) {
                    User user = dataSnapshot.child(phone).getValue(User.class);
                    if (user != null && user.getPassword().equals(password)) {
                        user.setPhone(phone);
                        getView().onSignInCallback(true, user);
                        getView().hideLoading();
                    } else {
                        getView().hideLoading();
                        getView().onSignInCallback(false, null);
                    }
                } else {
                    getView().hideLoading();
                    getView().onSignInCallback(false, null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().hideLoading();
                getView().onSignInCallback(false, null);
            }
        });
    }


}
