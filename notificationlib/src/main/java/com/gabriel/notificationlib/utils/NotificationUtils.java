package com.gabriel.notificationlib.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import com.gabriel.notificationlib.R;
import com.gabriel.notificationlib.constants.FirebaseMessagingConstants;
import com.gabriel.notificationlib.constants.NotificationConstants;

import java.util.ArrayList;

/**
 * Created by Gabriel on 12-09-2017.
 * <p>
 * Notification utils class to display notification
 */

public class NotificationUtils {

    private static final String TAG = "NotificationUtils";


    private PendingIntent destScreen;
    private ArrayList<NotificationCompat.Action> actions = new ArrayList<>();
    private static NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    // **Private constructor used to initialize all the variables based on the builder object
    private NotificationUtils(NotificationBuilder builder) {
        destScreen = builder.destScreen;
        actions = builder.actions;
        notificationManager = (NotificationManager) builder.context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(builder.context)
                .setColor(ContextCompat.getColor(builder.context, builder.color))
                .setSmallIcon(builder.iconID)
                .setLargeIcon(getLargeIcon(builder.context, builder.iconID))
                .setContentTitle(builder.titleText)
                .setContentText(builder.contentText)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(builder.contentText))
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setContentIntent(builder.destScreen)
                .setAutoCancel(true);
    }

    /**
     * this method is used to clear all the notifications of a context, from the notification tray
     */
    public static void clearAllNotifications(Context context) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    /**
     * This method shows a simple notification that works in the normal way
     */
    public void showSimpleNotification() {

        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

        notificationManager.notify(NotificationConstants.SIMPLE_NOTIFICATION_ID, notificationBuilder.build());

    }


    /**
     * This method is used to show a fullscreen notification much like a phone ringing.
     * If the screen is unlocked, this shows a heads-up display, if locked, it opens the
     * activity specified in the intent
     * <p>
     * Note 1: the notifications opens in full screen only if certain flags are set in the
     * receiver activity
     * Note 2: Its unwise to use this notifications for normal purposes, only use where you
     * really have to get the attention of the user. Its also wise to use this with actions
     * that can dismiss the notification
     */
    public void showFullScreenNotification() {

        notificationBuilder
                .setFullScreenIntent(destScreen, true)//Heads up notification if screen is unlocked, app opens if screen locked
                .setOngoing(true);

        if (!actions.isEmpty()) {
            for (NotificationCompat.Action action : actions) {
                notificationBuilder.addAction(action);
            }
        }

        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

        notificationManager.notify(NotificationConstants.AUTOLAUNCH_NOTIFICATION_ID, notificationBuilder.build());
    }


    /**
     * This shows a notification that can not be dismissed unless tapped on.
     */
    public void showContinuousNotification() {

        notificationBuilder.setOngoing(true);//Cannot dismiss by swipe

        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

        notificationManager.notify(NotificationConstants.CONTINOUS_NOTIFICATION_ID, notificationBuilder.build());
    }

    /**
     * This shows a notification with buttons that can be used to perform functions.
     * If there are no actions, it will show a simple notification
     */
    public void showActionNotification() {
        if (actions.isEmpty()) {
            Log.e(TAG, "No actions provided!!! Showing simple notification");
            showSimpleNotification();
            return;
        }
        for (NotificationCompat.Action action : actions) {
            notificationBuilder.addAction(action);
        }

        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

        notificationManager.notify(NotificationConstants.ACTION_NOTIIFICATION_ID, notificationBuilder.build());
    }


    private static Bitmap getLargeIcon(Context context, int iconID) {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, iconID);
    }


    /**
     * This is a Builder that can be used to create a Notification.
     * It uses the builder pattern to simplify things.
     */
    public static class NotificationBuilder {

        private Context context;
        private int color = -1;
        private int iconID = -1;
        private String contentText;
        private String titleText;
        private PendingIntent destScreen;
        ArrayList<NotificationCompat.Action> actions = new ArrayList<>();


        public NotificationBuilder(Context context, Intent destIntent, String contentText) {

            FirebaseMessagingConstants.INTENT_TYPE type = NotificationIntentUtils.getTypeOfIntent(destIntent);

            if (type == FirebaseMessagingConstants.INTENT_TYPE.ACTIVTY) {

                destScreen = NotificationIntentUtils.getPendingIntentForActivity(context, destIntent);

            } else if (type == FirebaseMessagingConstants.INTENT_TYPE.SERVICE) {

                destScreen = NotificationIntentUtils.getPendingIntentForService(context, destIntent);

            } else if (type == FirebaseMessagingConstants.INTENT_TYPE.BROADCAST) {

                destScreen = NotificationIntentUtils.getPendingIntentForBroadcast(context, destIntent);

            } else {

                Log.e(TAG, "Improper intent provided");
            }

            this.context = context;
            this.contentText = contentText;
        }

        /**
         * Setting a color for our notification
         *
         * @param color the color of the notification
         * @return a NotificationBuilder object that can be used to
         * continue building the NotificationUtils object
         */
        public NotificationBuilder setColor(@ColorRes int color) {
            this.color = color;
            return this;
        }

        /**
         * Setting an icon for the notification
         *
         * @param iconID the id of the notification
         * @return a NotificationBuilder object that can be used to
         * continue building the NotificationUtils object
         */
        public NotificationBuilder setIconID(int iconID) {
            this.iconID = iconID;
            return this;
        }

        /**
         * Setting a title for the notification
         *
         * @param titleText the title of the notification
         * @return a NotificationBuilder object that can be used to
         * continue building the NotificationUtils object
         */
        public NotificationBuilder setTitleText(String titleText) {
            this.titleText = titleText;
            return this;
        }

        /**
         * Adding actions(buttons) to start services when clicked
         *
         * @param actIntent    the intent to follow when the corresponding action button is pressed
         * @param actionIconID the icon of the action button
         * @param actionName   the name of the action button
         * @return a NotificationBuilder object that can be used to
         * continue building the NotificationUtils object
         */
        public NotificationBuilder addAction(@NonNull Intent actIntent, @NonNull Integer actionIconID, @NonNull String actionName) {

            NotificationCompat.Action action = getAction(actIntent, actionIconID, actionName, NotificationConstants.ACTION_TYPE.SIMPLE);

            if (action != null) {
                actions.add(action);
            } else {
                Log.e(TAG, "Unable to add action: " + actionName + ". Improper intent provided!!");
            }

            return this;
        }

        /**
         * This method is used to add an Inline action
         *
         * @param actIntent    the original intent that is to be used by the inline action
         * @param defIntent    the default intent that is to be used in case the device os
         * @param actionIconID the icon id of the action
         * @param actionName   the name of the action - Note: This will also be used as a key to retrieve text entered in the inline action
         * @return an action object specific for inline usage
         */
        public NotificationBuilder addInlineAction(@NonNull Intent actIntent, @NonNull Intent defIntent, @NonNull Integer actionIconID, @NonNull String actionName) {

            Intent intent;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent = actIntent;
            } else {
                intent = defIntent;
            }

            NotificationCompat.Action action = getAction(intent, actionIconID, actionName, NotificationConstants.ACTION_TYPE.INLINE);
            if (action != null) {
                actions.add(action);
            } else {
                Log.e(TAG, "Unable to add action : " + actionName + ". Improper intent provided!! ");
            }

            return this;
        }

        /**
         * this method returns a NotificationUtils object with all the info needed to make a notification
         *
         * @return a NotificationUtils object that can be used to display a notification
         */
        public NotificationUtils build() {
            initializeWithDefaults();
            return new NotificationUtils(this);
        }

        //Returns either a simple action or an inline action
        private NotificationCompat.Action getAction(Intent startIntent, int actionIcon, String actionName, NotificationConstants.ACTION_TYPE actionType) {

            FirebaseMessagingConstants.INTENT_TYPE type = NotificationIntentUtils.getTypeOfIntent(startIntent);
            PendingIntent pIntent;

            switch (type) {
                case ACTIVTY:
                    pIntent = NotificationIntentUtils.getPendingIntentForActivity(context, startIntent);
                    break;
                case SERVICE:
                    pIntent = NotificationIntentUtils.getPendingIntentForService(context, startIntent);
                    break;
                case BROADCAST:
                    pIntent = NotificationIntentUtils.getPendingIntentForBroadcast(context, startIntent);
                    break;
                default:
                    return null;
            }

            if (actionType == NotificationConstants.ACTION_TYPE.INLINE) {

                RemoteInput remoteInput = new RemoteInput.Builder(actionName).setLabel(actionName).build();

                return new NotificationCompat.Action.Builder(actionIcon, actionName, pIntent)
                        .addRemoteInput(remoteInput)
                        .setAllowGeneratedReplies(true)
                        .build();
            }
            return new NotificationCompat.Action(actionIcon, actionName, pIntent);
        }

        /*This method is used to initialize variables that were not initialized through the building
         process, with default values*/
        private void initializeWithDefaults() {
            if (color < 0) color = R.color.colorPrimary;
            if (iconID < 0) iconID = R.mipmap.ic_launcher;
            if (titleText == null) titleText = context.getString(R.string.app_name);
        }

    }
}
