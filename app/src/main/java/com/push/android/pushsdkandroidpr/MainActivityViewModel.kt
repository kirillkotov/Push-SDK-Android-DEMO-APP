package com.push.android.pushsdkandroidpr

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.*
import com.push.android.pushsdkandroid.PushSDK
import com.push.android.pushsdkandroidpr.utils.Event

class MainActivityViewModel(application: Application, private val preferences: SharedPreferences) : AndroidViewModel(
    application
) {

    //exposing ViewModel to SharedPreferences
    //didn't create repository because it's all simple anyways

    private lateinit var pushSDK : PushSDK

    /**
     * Stuff to show in the output
     */
    private val mResponseMessages = MutableLiveData<String>()
    val responseMessages: LiveData<String>
        get() = mResponseMessages

    fun postResponseMessage(msg: String) {
        mResponseMessages.postValue(msg)
    }

    /**
     * Clear the output text area
     */
    fun clearOutput() {
        postResponseMessage("Empty yet")
    }

    /**
     * system messages
     */
    private val mSystemMessages = MutableLiveData<Event<String>>()
    val systemMessages: LiveData<Event<String>>
        get() = mSystemMessages

    /**
     * api URL
     */
    val mApiUrl = MutableLiveData<String>()
    val ApiUrl: LiveData<String>
        get() = mApiUrl

    /**
     * client API key (given to you)
     */
    val mClientApiKey = MutableLiveData<String>()
    val ClientApiKey: LiveData<String>
        get() = mClientApiKey

    /**
     * app fingerprint (given to you)
     */
    val mAppFingerprint = MutableLiveData<String>()
    val AppFingerprint: LiveData<String>
        get() = mAppFingerprint

    /**
     * Device phone number
     */
    val mUserMsisdn = MutableLiveData<String>()
    val userMsisdn: LiveData<String>
        get() = mUserMsisdn

    /**
     * Device password (unused)
     */
    val mUserPassword = MutableLiveData<String>()
    val userPassword: LiveData<String>
        get() = mUserPassword

    init {
        //mApiUrl.value = "https://example.com/api/3.0/"
        mUserMsisdn.value = "88002000600"
        mUserPassword.value = "password"
        val url = preferences.getString("url", "")
        val clientApiKey = preferences.getString("clientApiKey", "")
        val appFingerprint = preferences.getString("appFingerprint", "")

        if (!url.isNullOrEmpty() && !clientApiKey.isNullOrEmpty() && !appFingerprint.isNullOrEmpty()) {
            mApiUrl.value = url
            mClientApiKey.value = clientApiKey
            mAppFingerprint.value = appFingerprint
            initSDK()
        }
        else {
            getApplication<Application>().getString(R.string.base_url).apply {
                if (isNotEmpty()) {
                    mApiUrl.value = this
                }
            }
            getApplication<Application>().getString(R.string.client_API_key).apply {
                if (isNotEmpty()) {
                    mClientApiKey.value = this
                }
            }
            getApplication<Application>().getString(R.string.app_fingerprint).apply {
                if (isNotEmpty()) {
                    mAppFingerprint.value = this
                }
            }
        }

        clearOutput()
    }

    /**
     * Reset credentials to default xml values
     */
    fun resetCredentials() {
        getApplication<Application>().getString(R.string.base_url).apply {
            if (isNotEmpty()) {
                mApiUrl.value = this
            }
        }
        getApplication<Application>().getString(R.string.client_API_key).apply {
            if (isNotEmpty()) {
                mClientApiKey.value = this
            }
        }
        getApplication<Application>().getString(R.string.app_fingerprint).apply {
            if (isNotEmpty()) {
                mAppFingerprint.value = this
            }
        }
        preferences.edit {
            putString("url", mApiUrl.value)
            putString("clientApiKey", mClientApiKey.value)
            putString("appFingerprint", mAppFingerprint.value)
        }
    }

    ////////////////////////////////////////
    // SDK methods
    ////////////////////////////////////////

    /**
     * Init the PushSDK
     */
    fun initSDK() {
        if (!mApiUrl.value.isNullOrEmpty() && !mClientApiKey.value.isNullOrEmpty() && !mAppFingerprint.value.isNullOrEmpty()) {
            preferences.edit {
                putString("url", mApiUrl.value)
                putString("clientApiKey", mClientApiKey.value)
                putString("appFingerprint", mAppFingerprint.value)
            }
            mSystemMessages.postValue(
                Event(
                    "initSDK"
                )
            )
            //init the SDK using constructor
            pushSDK = PushSDK(
                getApplication(),
                preferences.getString("url", "").toString(),
                PushSDK.LogLevels.PUSHSDK_LOG_LEVEL_DEBUG
            )
        }
        else {
            mSystemMessages.postValue(
                Event(
                    "no_credentials"
                )
            )
        }
    }

    /**
     * Register current device for the app within the MsgHub
     */
    fun registerDevice() {
        val answer = pushSDK.registerNewDevice(
            preferences.getString("clientApiKey", "")!!,
            preferences.getString("appFingerprint", "")!!,
            mUserMsisdn.value ?: "",
            mUserPassword.value ?: ""
        )
        postResponseMessage(answer.toString())
    }

    /**
     * Update registration
     */
    fun updateRegistration() {
        val answer = pushSDK.updateRegistration()
        postResponseMessage(answer.toString())
    }

    /**
     * Unregister current device for the app within MsgHub
     */
    fun unregisterDevice() {
        val answer = pushSDK.unregisterCurrentDevice()
        postResponseMessage(answer.toString())
    }

    /**
     * Unregister all devices for the app within MsgHub
     */
    fun unregisterAllDevices() {
        val answer = pushSDK.unregisterAllDevices()
        postResponseMessage(answer.toString())
    }

    /**
     * Get a list of all devices registered with the same number for the app within MsgHub
     */
    fun getAllDevices() {
        val answer = pushSDK.getAllRegisteredDevices()
        postResponseMessage(answer.toString())
    }

    /**
     * Check message queue, if queue is not empty - it will be sent via broadcast
     */
    fun checkMessageQueue() {
        val answer = pushSDK.checkMessageQueue()
        //postResponseMessage(answer.toString())
    }

    /**
     * Send a test message
     */
    fun sendTestMessage() {
        val answer = pushSDK.sendMessageAndReceiveCallback("message_id", "message_text")
        postResponseMessage(answer.toString())
    }

    /**
     * Send delivery report for a message
     */
    fun sendMessageDeliveryReport() {
        sendMessageDeliveryReport("message_id")
    }

    /**
     * Send delivery report for a message
     * @param messageId message ID
     */
    fun sendMessageDeliveryReport(messageId: String) {
        val answer = pushSDK.sendMessageDeliveryReport("message_id")
        postResponseMessage(answer.toString())
    }

    /**
     * Get message history within specified time period
     */
    fun getMessageHistory() {
        val answer = pushSDK.getMessageHistory(1*60*60)
        postResponseMessage(answer.toString())
    }

    /**
     * Change your device phone number (unused)
     */
    fun changeNumber() {
        val answer = pushSDK.rewriteMsisdn(userMsisdn.value.toString())
        postResponseMessage(answer.toString())
    }

//    /**
//     * Change your device's password
//     */
//    fun changePassword() {
//        val answer = pushSDK.rewritePassword(userPassword.value.toString())
//        postResponseMessage(answer.toString())
//    }
}