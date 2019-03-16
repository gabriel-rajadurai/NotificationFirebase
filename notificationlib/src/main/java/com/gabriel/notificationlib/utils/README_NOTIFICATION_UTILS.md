# NotificationUtils

This class contains a simplified notification builder that can be used to display various kinds of notifications.
Its just a wrapper around the NotificationCompat.Builder with a simplified setup.

### Usage:

- To start displaying notifications, you must create a NotificationUtils object through a builder process,

     ```
      
      new NotificationUtils.NotificationBuilder(context, intent, contentText).build();// Note, before building the utils object, you can setTitle, addAction, etc...
         
     ```
     
- With the resultant ``` NotificationUtils ``` object, one can call the following methods to show different kinds of 
notifications,
     
     ```
        
        notificationUtils.showSimpleNotification();
        notificationUtils.showContinousNotification();// a notification that gets dismissed only when tapped on.
        notificationUtils.showFullScreenNotification();// a fullscreen notification much like a phone ring, for this to work the resultant activity needs certain flags
        notificationUtils.showActionNotification();// a notification with buttons to perform actions
        
     ```
     
- Some examples,

     ```
        
        //Simple Notification
        
        Intent intent = new Intent(this, HomeActivity.class); //You can also use a service or broadcast receiver.
        new NotificationUtils.NotificationBuilder(this, intent, "test").build().showSimpleNotification();
        
        //Continous Notification
        
        Intent intent = new Intent(this, HomeActivity.class); 
        new NotificationUtils.NotificationBuilder(this, intent, "test").build().showContinousNotification();
        
        //Action Notification
        
        Intent actionIntent = new Intent(this, NotificationReminderService.class);
        actionIntent.setAction(ACTION_KEY);
        new NotificationUtils.NotificationBuilder(this, intent, "test")
                    .setTitleText("Test notification")
                    .addAction(actionIntent, R.drawable.action_icon, "Action Name")
                    .build().showActionNotification();
                    
        //You can also show an inline action as shown below                    
        
        Intent defIntent = new Intent(this, LoginActivity.class); //Default intent to be used as medium if the device OS vversion does not support inline actions
        defIntent.setAction(ACTION_KEY);
        new NotificationUtils.NotificationBuilder(this, intent, data.get("detail"))
                 .setTitleText("Test notification")
                 .addInlineAction(actionIntent,defIntent,R.drawable.ic_reply_black_24dp,"Reply")
                 .build().showActionNotification();    
        
     ```
     
     


     
     