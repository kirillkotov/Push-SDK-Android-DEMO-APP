package com.push.android.pushsdkandroidpr

import com.google.firebase.messaging.RemoteMessage
import com.push.android.pushsdkandroid.PushKFirebaseService

/**
 * PushKFirebaseService, can also be used as a regular FirebaseMessagingService
 */
class MyPushKMessagingService : PushKFirebaseService(
    summaryNotificationTitleAndText = Pair("title", "text"),
    notificationIconResourceId = android.R.drawable.ic_notification_overlay,
    notificationStyle = NotificationStyle.LARGE_ICON
) {

    /**
     * Called when data push is received from the Messaging Hub
     */
    override fun onReceiveDataPush(appIsInForeground: Boolean, remoteMessage: RemoteMessage) {
        super.onReceiveDataPush(appIsInForeground, remoteMessage)
    }

    /**
     * Called when notification is displayed (Messaging hub only)
     */
    override fun onDisplayNotification(appIsInForeground: Boolean, remoteMessage: RemoteMessage) {
        super.onDisplayNotification(appIsInForeground, remoteMessage)
    }

    /**
     * Called when notification should be displayed, but will not be (Messaging hub only)
     */
    override fun onUnableToDisplayNotification(
        areNotificationsEnabled: Boolean,
        appIsInForeground: Boolean,
        remoteMessage: RemoteMessage
    ) {
        super.onUnableToDisplayNotification(
            areNotificationsEnabled,
            appIsInForeground,
            remoteMessage
        )
    }

    /**
     * Called when ANY message received
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        //put your code here to use as default FirebaseMessagingService method
    }

    /**
     * Called when ANY firebase messages are deleted (will not be delivered)
     */
    override fun onDeletedMessages() {
        super.onDeletedMessages()
        //put your code here to use as default FirebaseMessagingService method
    }

    /**
     * Called when firebase assigns new token
     */
    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        //put your code here to use as default FirebaseMessagingService method
    }

}
