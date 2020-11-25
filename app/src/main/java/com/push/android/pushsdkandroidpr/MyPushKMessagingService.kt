package com.push.android.pushsdkandroidpr

import com.google.firebase.messaging.RemoteMessage
import com.push.android.pushsdkandroid.PushKFirebaseService

class MyPushKMessagingService : PushKFirebaseService(
    summaryNotificationTitleAndText = Pair("title", "text"),
    notificationIconResourceId = android.R.drawable.ic_notification_overlay,
    notificationStyle = NotificationStyle.LARGE_ICON
) {

    override fun onReceiveDataPush(appIsInForeground: Boolean, remoteMessage: RemoteMessage) {
        super.onReceiveDataPush(appIsInForeground, remoteMessage)
    }

    override fun onDisplayNotification(appIsInForeground: Boolean, remoteMessage: RemoteMessage) {
        super.onDisplayNotification(appIsInForeground, remoteMessage)
    }

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

}
