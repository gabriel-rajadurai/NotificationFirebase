package com.gabriel.notificationfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gabriel.notificationfirebase.login.LoginFragment;
import com.gabriel.notificationlib.FirebaseMessageHandler;
import com.gabriel.notificationlib.Screen;
import com.gabriel.notificationlib.constants.FirebaseMessagingConstants;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnLoginClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadLoginFragment();
        if (FirebaseMessageHandler.isUserLoggedIn()) {
            onLoginClicked();
        }
    }

    private void loadLoginFragment() {
        Fragment fragment = new LoginFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fg_container, fragment);
        ft.commit();
    }


    @Override
    public void onLoginClicked() {
        Intent intent = new Intent(this, HomeActivity.class);

        if (getIntent().getSerializableExtra(FirebaseMessagingConstants.SCREEN_TO_OPEN) != null) {
            Screen screen = (Screen) getIntent().getSerializableExtra(FirebaseMessagingConstants.SCREEN_TO_OPEN);
            if (!screen.getActivityToOpen().equals(getClass())) {
                intent = new Intent(this, screen.getActivityToOpen());
                intent.putExtra(FirebaseMessagingConstants.DATA, getIntent().getSerializableExtra(FirebaseMessagingConstants.DATA));
                intent.putExtra(FirebaseMessagingConstants.SCREEN_TO_OPEN, screen);
            }
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRegisterClicked() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
