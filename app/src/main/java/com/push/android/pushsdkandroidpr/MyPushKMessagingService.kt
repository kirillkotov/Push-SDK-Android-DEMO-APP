package com.push.android.pushsdkandroidpr

import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.push.android.pushsdkandroid.PushKFirebaseService
import com.push.android.pushsdkandroid.managers.PushSdkNotificationManager

/**
 * PushKFirebaseService, can also be used as a regular FirebaseMessagingService
 */
//class MyPushKMessagingService : PushKFirebaseService(
//    summaryNotificationTitleAndText = Pair("title", "text"),
//    notificationIconResourceId = android.R.drawable.ic_notification_overlay
//) {
class MyPushKMessagingService : PushKFirebaseService(
        summaryNotificationTitleAndText = Pair("title", "text"),
        notificationIconResourceId = android.R.drawable.ic_notification_overlay
) {

    /**
     * Called when data push is received from the Messaging Hub
     */
    override fun onReceiveDataPush(
            appIsInForeground: Boolean,
            isDoNotDisturbModeActive: Boolean,
            areNotificationsEnabled: Boolean,
            isNotificationChannelMuted: Boolean,
            remoteMessage: RemoteMessage
    ) {
        super.onReceiveDataPush(
                appIsInForeground,
                isDoNotDisturbModeActive,
                areNotificationsEnabled,
                isNotificationChannelMuted,
                remoteMessage
        )

        //can be used to configure, for example set "isDoNotDisturbModeActive" to false,
        // to send notifications in "Do not disturb mode" anyways
//        super.onReceiveDataPush(
//            appIsInForeground = appIsInForeground,
//            isDoNotDisturbModeActive = false,
//            areNotificationsEnabled = areNotificationsEnabled,
//            isNotificationChannelMuted = isNotificationChannelMuted,
//            remoteMessage = remoteMessage
//        )
    }

    /**
     * Prepares NotificationCompat.Builder object for showing
     */
    override fun prepareNotification(data: Map<String, String>): NotificationCompat.Builder? {
        return super.prepareNotification(data)
        //can customize NotificationCompat.Builder object here, e.g.:
//        val notificationConstruct = pushSdkNotificationManager.constructNotification(data, PushSdkNotificationManager.NotificationStyle.NO_STYLE)
//        notificationConstruct?.apply {
//            setContentTitle("some new text")
//            setContentText("some new text")
//            setStyle(NotificationCompat.BigTextStyle())
//        }
//        return notificationConstruct
    }

    /**
     * Callback - when notification is sent
     */
    override fun onNotificationSent(
            appIsInForeground: Boolean,
            isDoNotDisturbModeActive: Boolean,
            areNotificationsEnabled: Boolean,
            isNotificationChannelMuted: Boolean,
            remoteMessage: RemoteMessage
    ) {
        super.onNotificationSent(
                appIsInForeground,
                isDoNotDisturbModeActive,
                areNotificationsEnabled,
                isNotificationChannelMuted,
                remoteMessage
        )
    }

    /**
     * Callback - when notification will not be sent
     */
    override fun onNotificationWontBeSent(
            appIsInForeground: Boolean,
            isDoNotDisturbModeActive: Boolean,
            areNotificationsEnabled: Boolean,
            isNotificationChannelMuted: Boolean,
            remoteMessage: RemoteMessage
    ) {
        super.onNotificationWontBeSent(
                appIsInForeground,
                isDoNotDisturbModeActive,
                areNotificationsEnabled,
                isNotificationChannelMuted,
                remoteMessage
        )
    }

    ///////////////////////////////////////////////
    // FirebaseMessagingService methods
    ///////////////////////////////////////////////

    /**
     * Called when ANY message received
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //remoteMessage.data.toString()
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
