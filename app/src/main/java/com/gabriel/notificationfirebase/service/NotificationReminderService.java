package com.gabriel.notificationfirebase.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import com.gabriel.notificationlib.constants.NotificationConstants;
import com.gabriel.notificationlib.utils.NotificationUtils;

/**
 * Created by Gabriel on 12-09-2017.
 */

public class NotificationReminderService extends IntentService
{
    private static final String TAG = "NotificationReminderSer";

    public NotificationReminderService() {
        super("NotificationReminderIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        Log.d(TAG, "onHandleIntent: ");
        String action = intent.getAction();

        if(NotificationConstants.CLOSE_NOTIFICATION_ACTION.equals(action)) {
            NotificationUtils.clearAllNotifications(this);
            Log.d(TAG, "closed Notification: ");
        }
        else if(NotificationConstants.OPEN_MESSAGE_ACTION.equals(action))
        {
            Log.d(TAG, "opened Message: ");
            NotificationUtils.clearAllNotifications(this);
        }
        else if(NotificationConstants.REPLY_ACTION.equals(action)){
            Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
            if (remoteInput != null) {
                Log.d(TAG, "onHandleIntent: " + remoteInput.getCharSequence("Reply"));
            }
            NotificationUtils.clearAllNotifications(this);
        }


    }
}
