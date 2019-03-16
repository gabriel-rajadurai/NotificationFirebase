package com.gabriel.notificationlib.listeners;

import com.gabriel.notificationlib.IntentComponent;
import com.gabriel.notificationlib.Screen;

import java.util.LinkedHashMap;

/**
 * Created by Gabriel on 01-12-2017.
 * Interface for callbacks to service after a Firebase message is handled
 */

public interface OnHandleMessageCompleteListener {

    void onSuccess(IntentComponent componentToStart, LinkedHashMap<String, String> data, boolean isAppInForeground);

    void onFailure(String error, LinkedHashMap<String, String> data);

}
