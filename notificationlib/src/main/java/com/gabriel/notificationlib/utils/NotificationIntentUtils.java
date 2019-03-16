package com.gabriel.notificationlib.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.gabriel.notificationlib.constants.FirebaseMessagingConstants;

/**
 * Created by Gabriel on 04-12-2017.
 */

class NotificationIntentUtils {

    /**
     * Returns a pending intent for activity
     *
     * @param context             the context of the source
     * @param startActivityIntent the destination activity intent
     * @return a pending intent
     */
    static PendingIntent getPendingIntentForActivity(Context context, Intent startActivityIntent) {

        return PendingIntent.getActivity(
                context,
                (int) (System.currentTimeMillis() & 0xff),
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Returns a pending intent for service
     *
     * @param context            the context of the source
     * @param startServiceIntent the destination service intent
     * @return a pending intent
     */
    static PendingIntent getPendingIntentForService(Context context, Intent startServiceIntent) {

        return PendingIntent.getService(
                context,
                (int) (System.currentTimeMillis() & 0xff),
                startServiceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Returns a pending intent for broadcst
     *
     * @param context              the context of the source
     * @param startBroadcastIntent the destination broadcast intent
     * @return a pending intent
     */
    static PendingIntent getPendingIntentForBroadcast(Context context, Intent startBroadcastIntent) {

        return PendingIntent.getBroadcast(
                context,
                (int) (System.currentTimeMillis() & 0xff),
                startBroadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Returns the type of intent
     *
     * @param intent the intent whose type is to be determined
     * @return and INTENT_TYPE value specifying the type of intent
     */
    static FirebaseMessagingConstants.INTENT_TYPE getTypeOfIntent(@NonNull Intent intent) {

        try {
            Class superClass = Class.forName(intent.getComponent().getClassName());

            while ((superClass = superClass.getSuperclass()) != null) {

                if (superClass.equals(Service.class)) {

                    return FirebaseMessagingConstants.INTENT_TYPE.SERVICE;

                } else if (superClass.equals(Activity.class) || superClass.equals(AppCompatActivity.class)) {

                    return FirebaseMessagingConstants.INTENT_TYPE.ACTIVTY;

                } else if (superClass.equals(BroadcastReceiver.class)) {

                    return FirebaseMessagingConstants.INTENT_TYPE.BROADCAST;
                }
            }

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

        return FirebaseMessagingConstants.INTENT_TYPE.NO_SUCH_TYPE;
    }

}
