package com.gabriel.notificationlib;

import android.app.ActivityManager;
import android.content.Context;

import com.gabriel.notificationlib.constants.FirebaseMessagingConstants;
import com.gabriel.notificationlib.listeners.OnHandleMessageCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.RemoteMessage;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Gabriel on 30-11-2017.
 * <p>
 * this class handles a firebase data message and returns back with a callback containing information of
 * the screen to open on successfully handling a message
 */

public class FirebaseMessageHandler {

    private static LinkedHashMap<String, IntentComponent> components = new LinkedHashMap<>();
    private static IntentComponent defaultComponent;

    /**
     * Checks if the user is logged in
     *
     * @return true if user is logged in, false if logged out
     */
    public static boolean isUserLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }


    /**
     * This method is used to register components against a specific key
     *
     * @param key    the key of the activity
     * @param component a custom object that has details of activity and fragment, or a service or a broadcast
     */
    public static void registerComponent(String key, IntentComponent component) {
        components.put(key, component);
    }

    /**
     * This method is used to register a default component, that can be used if there are no components registered against the key
     * sent by the server.
     *
     * @param defaultComponent The default component to start
     */
    public static void registerDefaultComponent(IntentComponent defaultComponent) {
        FirebaseMessageHandler.defaultComponent = defaultComponent;
    }


    /**
     * This method handles the message received from Firebase and returns the screen to open, through a callback
     *
     * @param remoteMessage the message received from firebase
     * @param listener      the callback that returns to the service with the resultant screen on success and error message
     *                      on failure
     */
    public static void handleFirebaseMessage(RemoteMessage remoteMessage, Context context, OnHandleMessageCompleteListener listener) {

        if (remoteMessage.getData() != null) {

            LinkedHashMap<String, String> data = new LinkedHashMap<>(remoteMessage.getData());

            if (data.get("screen") != null) {

                String componentKey = data.get("screen");

                if (components.get(componentKey) != null) {

                    listener.onSuccess(components.get(componentKey), data, isApplicationInForeground(context));
                    return;
                }
            }

            listener.onFailure(FirebaseMessagingConstants.ERROR, data);
        }
    }

    /**
     * This method is used to get the default screen
     *
     * @return default Screen object
     */
    public static IntentComponent getDefaultComponent() {
        return defaultComponent;
    }

    /**
     * This method returns the count of registered screens
     *
     * @return no of registered screens
     */
    public static int getRegisteredScreensCount() {
        return components.size();
    }

    /**
     * This method checks if an app is in foreground
     *
     * @param context the context of the app
     * @return true if app is in foreground, false if background
     */
    private static boolean isApplicationInForeground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager == null) {
            return false;
        }

        boolean isActivityFound = false;
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

        if (appProcesses.get(0).importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                appProcesses.get(0).processName.equalsIgnoreCase(context.getPackageName())) {

            isActivityFound = true;
        }

        return isActivityFound;
    }


}
