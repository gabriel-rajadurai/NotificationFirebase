# NotifcationFirebase

This project contains code to handle received Firebase data messages to identify which component to start in your app.
The component could be an activity or a service or a broadcast receiver. This works in sync with ``` FirebaseMessagingService ```.

### Usage:

- Firstly, Register all components with a unique key, using the following methods, It is advisable to do this within ``` onMessageReceived() ``` override,

    ```

    FirebaseMessagingUtils.registerComponent(key,intentComponent)

    ```
    
    An example is given below,

    ```

        FirebaseMessageHandler.registerComponent("home-fragment-one",
                IntentComponent.getScreenComponent(Screen.getSupportScreen(R.id.home_fg_container,
                        HomeActivity.class,
                        FragmentOne.class)));

    ```

- Next, Inside ``` onMessageReceived() ``` override of the ``` FirebaseMessagingService ```, invoke the message handler

    ```

    FirebaseMessagingUtils.handleMessage(remoteMessage, OnHandleMessageCompleteListener)// Handles the message and returns back with a  callback

    ```

    Example,

    ```

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        
        FirebaseMessageHandler.registerComponent("home-fragment-one",
                IntentComponent.getScreenComponent(Screen.getSupportScreen(R.id.home_fg_container,
                        HomeActivity.class,
                        FragmentOne.class)));

        FirebaseMessageHandler.registerComponent("home-fragment-two",
                IntentComponent.getScreenComponent(Screen.getSupportScreen(R.id.home_fg_container,
                        HomeActivity.class,
                        FragmentTwo.class)));
                
        FirebaseMessagingUtils.handleFirebaseMessage(remoteMessage, this);
    }

    ```

- Finally, Override the callback methods in the service, an example is given below,

    ```

    @Override
    public void onSuccess(IntentComponent intentComponent, LinkedHashMap<String, String> data, boolean isAppInForeground) {
        if(!isAppInForeground) // You can use this boolean to prevent notification if app is in foreground
            sendNotification(intentComponent, data);
    }

    @Override
    public void onFailure(String error, LinkedHashMap<String, String> data) {
        Log.d(TAG, "onFailure: " + error);
    }
    
    ```


A full example can be found in the ``` NotificationService ``` class.

