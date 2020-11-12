package com.push.android.pushsdkandroidpr

import com.google.firebase.messaging.RemoteMessage
import com.push.android.pushsdkandroid.PushKFirebaseService

class MyPushKFirebaseService : PushKFirebaseService(
    PARAM_NOTIFICATIONS_SUMMARY_TITLE_AND_TEXT = Pair("title", "text")
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