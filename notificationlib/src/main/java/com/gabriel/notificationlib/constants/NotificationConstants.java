package com.gabriel.notificationlib.constants;

/**
 * Created by Gabriel on 01-12-2017.
 */

public interface NotificationConstants {

    int SIMPLE_NOTIFICATION_ID = 1133;
    int AUTOLAUNCH_NOTIFICATION_ID = 1136;

    int CONTINOUS_NOTIFICATION_ID = 1134;
    int ACTION_NOTIIFICATION_ID = 1135;

    int ACTION_CLOSE_PENDINGINTENT_ID = 100;
    int ACTION_OPEN_PENDINGINTENT_ID = 101;

    String CLOSE_NOTIFICATION_ACTION = "close notification";
    String OPEN_MESSAGE_ACTION = "open notification";
    String REPLY_ACTION = "reply notification";


    enum ACTION_TYPE {
        SIMPLE,
        INLINE
    }


}
