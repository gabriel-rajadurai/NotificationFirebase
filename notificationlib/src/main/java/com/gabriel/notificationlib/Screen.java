package com.gabriel.notificationlib;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

/**
 * Created by Gabriel on 30-11-2017.
 * <p>
 * Model class that represents a screen in the app
 */

public class Screen implements Serializable {

    private int fragmentContainerID;
    private Class activityToOpen;
    private Class fragmentToOpen;

    private Screen(int fragmentContainerID, Class activityToOpen, Class fragmentToOpen) {
        this.fragmentContainerID = fragmentContainerID;
        this.activityToOpen = activityToOpen;
        this.fragmentToOpen = fragmentToOpen;
    }

    /**
     * Returns a screen object that wraps the class files of the support activty and fragments
     *
     * @param fgContainerID  fragment container id
     * @param activityToOpen the support activty screen that has to be opened
     * @param fragmentToOpen the support fragment screen to be opened
     * @return a screen object with all the details of an actual screen in the app
     */
    public static Screen getSupportScreen(@IdRes Integer fgContainerID, @NonNull Class<? extends AppCompatActivity> activityToOpen,
                                          Class<? extends Fragment> fragmentToOpen) {
        return new Screen(fgContainerID, activityToOpen, fragmentToOpen);
    }

    /**
     * Returns a screen object that wraps the class files of the activty and fragments
     *
     * @param fgContainerID  fragment container id
     * @param activityToOpen the activty screen that has to be opened
     * @param fragmentToOpen the fragment screen to be opened
     * @return a screen object with all the details of an actual screen in the app
     */
    public static Screen getScreen(@IdRes Integer fgContainerID, @NonNull Class<? extends Activity> activityToOpen,
                                   Class<? extends android.app.Fragment> fragmentToOpen) {
        return new Screen(fgContainerID, activityToOpen, fragmentToOpen);
    }

    public int getFragmentContainerID() {
        return fragmentContainerID;
    }

    public Class getActivityToOpen() {
        return activityToOpen;
    }

    public Class getFragmentToOpen() {
        return fragmentToOpen;
    }
}
