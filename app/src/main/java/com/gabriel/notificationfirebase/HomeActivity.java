package com.gabriel.notificationfirebase;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gabriel.notificationfirebase.home.FragmentOne;
import com.gabriel.notificationfirebase.home.FragmentTwo;
import com.gabriel.notificationlib.Screen;
import com.gabriel.notificationlib.constants.FirebaseMessagingConstants;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements FragmentOne.OnFragmentInteractionListener,
        FragmentTwo.OnFragmentInteractionListener {

    private FirebaseAuth auth;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btLogout = findViewById(R.id.logout_bt);
        auth = FirebaseAuth.getInstance();
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                openLoginScreen();
            }
        });
        openFragmentScreen();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.d(TAG, "onNewIntent: ");
        openFragmentScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void openFragmentScreen() {
        Intent intent = getIntent();
        HashMap<String, String> data = null;
        Screen screenToOpen = null;


        data = (HashMap<String,String>) intent.getSerializableExtra(FirebaseMessagingConstants.DATA);
        screenToOpen = (Screen) intent.getSerializableExtra(FirebaseMessagingConstants.SCREEN_TO_OPEN);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = new FragmentOne();

        if (screenToOpen != null && screenToOpen.getFragmentToOpen() != null) {
            try {

                fragment = (Fragment) screenToOpen.getFragmentToOpen().newInstance();
                ft.add(screenToOpen.getFragmentContainerID(), fragment);

                Log.d(TAG, "openFragmentScreen:  " + fragment);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            ft.add(R.id.home_fg_container, fragment);

            Log.d(TAG, "openFragmentScreen: ");
        }
        ft.commit();
    }

    private void openLoginScreen() {
        onBackPressed();
    }

    private void openFragmentTwo(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = new FragmentTwo();
        ft.add(R.id.home_fg_container, fragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
