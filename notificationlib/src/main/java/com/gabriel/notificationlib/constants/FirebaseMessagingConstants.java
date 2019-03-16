package com.gabriel.notificationlib.constants;

/**
 * Created by Gabriel on 30-11-2017.
 */

public interface FirebaseMessagingConstants {

    String SCREEN_TO_OPEN = "screenToOpen";
    String DATA = "data";
    String ERROR = "Cannot find screen. Screen either not registered or does not exist!!; ";

    enum INTENT_TYPE {
        SERVICE,
        ACTIVTY,
        BROADCAST,
        NO_SUCH_TYPE
    }
}
