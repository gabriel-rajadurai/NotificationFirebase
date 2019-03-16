package com.gabriel.notificationfirebase.service;

import android.content.Intent;

import com.gabriel.notificationfirebase.HomeActivity;
import com.gabriel.notificationfirebase.LoginActivity;
import com.gabriel.notificationfirebase.R;
import com.gabriel.notificationfirebase.home.FragmentOne;
import com.gabriel.notificationfirebase.home.FragmentTwo;
import com.gabriel.notificationfirebase.login.LoginFragment;
import com.gabriel.notificationlib.FirebaseMessageHandler;
import com.gabriel.notificationlib.IntentComponent;
import com.gabriel.notificationlib.Screen;
import com.gabriel.notificationlib.constants.FirebaseMessagingConstants;
import com.gabriel.notificationlib.constants.NotificationConstants;
import com.gabriel.notificationlib.listeners.OnHandleMessageCompleteListener;
import com.gabriel.notificationlib.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.LinkedHashMap;


public class NotificationService extends FirebaseMessagingService implements OnHandleMessageCompleteListener {

    private static final String TAG = "NotificationService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (FirebaseMessageHandler.getRegisteredScreensCount() == 0) {

            //Registering all screens in the FirebaseMessageHandler
            registerScreens();
        }

        //Handle the firebase message
        FirebaseMessageHandler.handleFirebaseMessage(remoteMessage, this, this);
    }

    private void registerScreens() {

        //Registering default screen to open if user is logged out
        FirebaseMessageHandler.registerDefaultComponent(IntentComponent.getScreenComponent(Screen.getSupportScreen(R.id.fg_container,
                LoginActivity.class,
                LoginFragment.class)));

        FirebaseMessageHandler.registerComponent("home-fragment-one",
                IntentComponent.getScreenComponent(Screen.getSupportScreen(R.id.home_fg_container,
                        HomeActivity.class,
                        FragmentOne.class)));

        FirebaseMessageHandler.registerComponent("home-fragment-two",
                IntentComponent.getScreenComponent(Screen.getSupportScreen(R.id.home_fg_container,
                        HomeActivity.class,
                        FragmentTwo.class)));

    }

    private void sendNotification(IntentComponent intentComponent, LinkedHashMap<String, String> data) {

        IntentComponent component = intentComponent;

        if (!FirebaseMessageHandler.isUserLoggedIn()) {
            component = FirebaseMessageHandler.getDefaultComponent();
        }

        Intent intent = new Intent(this, component.getComponentToStart());
        Screen screenToOpen = null;

        if (component.getIntentType() == FirebaseMessagingConstants.INTENT_TYPE.ACTIVTY) {
            screenToOpen = component.getScreenToOpen();
        }

        intent.putExtra(FirebaseMessagingConstants.DATA, data);
        intent.putExtra(FirebaseMessagingConstants.SCREEN_TO_OPEN, screenToOpen);

        Intent actionIntent = new Intent(this, NotificationReminderService.class);
        actionIntent.setAction(NotificationConstants.REPLY_ACTION);

        Intent defIntent = new Intent(this, LoginActivity.class);
        actionIntent.setAction(NotificationConstants.REPLY_ACTION);


        // Here we write the code for showing notifications
        new NotificationUtils.NotificationBuilder(this, intent, data.get("detail"))
                .setTitleText("Test notification")
                .addInlineAction(actionIntent,defIntent,R.drawable.ic_reply_black_24dp,"Reply")
                .build().showActionNotification();

    }


    @Override
    public void onSuccess(IntentComponent componentToStart, LinkedHashMap<String, String> data, boolean isAppInForeground) {
        //if(!isAppInForeground)
        sendNotification(componentToStart, data);
    }

    @Override
    public void onFailure(String error, LinkedHashMap<String, String> data) {

    }
}
