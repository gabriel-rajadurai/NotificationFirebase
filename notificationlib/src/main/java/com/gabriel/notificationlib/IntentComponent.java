package com.gabriel.notificationlib;

import android.app.Service;
import android.content.BroadcastReceiver;

import com.gabriel.notificationlib.constants.FirebaseMessagingConstants;

/**
 * Created by Gabriel on 06-12-2017.
 * <p>
 * This is a wrapper class around classes of activty, service or broadcast.
 * This is designed to be used as an intent's component( the 2nd argument that you put when you create an intent)
 */

public class IntentComponent {

    private Screen screenToOpen;
    private Class componentToStart;

    private FirebaseMessagingConstants.INTENT_TYPE intentType;

    // Private constructor to create a Screen component
    private IntentComponent(Screen sreenToOpen, FirebaseMessagingConstants.INTENT_TYPE intentType) {
        this.screenToOpen = sreenToOpen;
        this.intentType = intentType;
    }

    // Private constructor to create a service or a broadcast component
    private IntentComponent(Class componentToStart, FirebaseMessagingConstants.INTENT_TYPE intentType) {
        this.componentToStart = componentToStart;
        this.intentType = intentType;
    }

    /**
     * To get a screen component - to be used with intents that open activities.
     *
     * @param screenToOpen The Screen object containing the class files of activity and fragment.
     * @return an IntentComponent object for specific use with intents that open an Activity.
     */
    public static IntentComponent getScreenComponent(Screen screenToOpen) {
        return new IntentComponent(screenToOpen, FirebaseMessagingConstants.INTENT_TYPE.ACTIVTY);
    }

    /**
     * To get a service component - to be used with intents that start a service.
     *
     * @param serviceToStart The class file of a Service.
     * @return an IntentComponent object for specific use with intents that starts a Service.
     */
    public static IntentComponent getServiceComponent(Class<? extends Service> serviceToStart) {
        return new IntentComponent(serviceToStart, FirebaseMessagingConstants.INTENT_TYPE.SERVICE);
    }

    /**
     * To get a broadcast component - to be used with intents that start a broadcast.
     *
     * @param broadcastToOpen The class file of a BroadcastReceeiver
     * @return an IntentComponent object for specific use with intents that starts a Broadcast.
     */
    public static IntentComponent getBroadcastComponent(Class<? extends BroadcastReceiver> broadcastToOpen) {
        return new IntentComponent(broadcastToOpen, FirebaseMessagingConstants.INTENT_TYPE.BROADCAST);
    }

    /**
     * Returns the screen object.
     *
     * @return a screen object - to be used if and only if the INTENT_TYPE is ACTIVITY, otherwise it would return null.
     */
    public Screen getScreenToOpen() {
        return screenToOpen;
    }

    /**
     * Returns the class file of a component - It could either be and Activity class file or a Service or Broadcast class file.
     *
     * @return the class file of a component
     */
    public Class getComponentToStart() {
        if (intentType == FirebaseMessagingConstants.INTENT_TYPE.ACTIVTY) {
            return screenToOpen.getActivityToOpen();
        } else
            return componentToStart;
    }

    /**
     * Returnt the type of the Intent - it could be ACTIVITY or SERVICE or BROADCAST
     * @return returns the INTENT_TYPE
     */
    public FirebaseMessagingConstants.INTENT_TYPE getIntentType() {
        return intentType;
    }
}
