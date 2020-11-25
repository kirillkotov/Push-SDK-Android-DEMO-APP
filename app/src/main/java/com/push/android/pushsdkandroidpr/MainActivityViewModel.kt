package com.push.android.pushsdkandroidpr

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.util.Log
import androidx.core.content.edit
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.push.android.pushsdkandroid.PushSDK
import com.push.android.pushsdkandroid.core.ApiParams

class MainActivityViewModel(application: Application) : AndroidViewModel(
    application
) {

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
     * system messages (really didn't have time to put Event)
     */
    private val mSystemMessages = MutableLiveData<String>()
    val systemMessages: LiveData<String>
        get() = mSystemMessages

    /**
     * api URL
     */
    val mApiUrl = MutableLiveData<String>()
    val ApiUrl: LiveData<String>
        get() = mApiUrl

    /**
     * api URL
     */
    val mClientApiKey = MutableLiveData<String>()
    val ClientApiKey: LiveData<String>
        get() = mClientApiKey

    /**
     * api URL
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
     * Device password
     */
    val mUserPassword = MutableLiveData<String>()
    val userPassword: LiveData<String>
        get() = mUserPassword

    val preferences: SharedPreferences = getApplication<Application>().getSharedPreferences("demo", Context.MODE_PRIVATE)

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

    /**
     * Change credentials
     */
    fun changeCredentials() {
        resetCredentials()
        mSystemMessages.postValue("change_credentials")
    }

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
            mSystemMessages.postValue("initSDK")
            pushSDK = PushSDK(
                getApplication(),
                //mApiUrl.value ?: "",
                preferences.getString("url", "")!!,
                PushSDK.LogLevels.PUSHSDK_LOG_LEVEL_DEBUG
            )
        }
        else {
            mSystemMessages.postValue("no_credentials")
        }
        //val apiParams = ApiParams()
        //apiParams.baseURL = ""
//        try {
//            getString(R.string.base_url).apply {
//                if (isNotEmpty()) {
//                    apiParams.baseURL = this
//                }
//            }
//            getString(R.string.header_client_api_key).apply {
//                if (isNotEmpty()) {
//                    apiParams.headerClientApiKey = this
//                }
//            }
//            getString(R.string.header_app_fingerprint).apply {
//                if (isNotEmpty()) {
//                    apiParams.headerAppFingerprint = this
//                }
//            }
//            getString(R.string.header_session_id).apply {
//                if (isNotEmpty()) {
//                    apiParams.headerSessionId = this
//                }
//            }
//            getString(R.string.header_timestamp).apply {
//                if (isNotEmpty()) {
//                    apiParams.headerTimestamp = this
//                }
//            }
//            getString(R.string.header_authtoken).apply {
//                if (isNotEmpty()) {
//                    apiParams.headerAuthToken = this
//                }
//            }
//        }
//        catch (e: Resources.NotFoundException) {
//            Log.d("PushSDKinit", "headers are not specified, skipping")
//        }


        //pushSDK = PushSDK(getApplication(), mApiUrl.value ?: "", PushSDK.LogLevels.PUSHSDK_LOG_LEVEL_DEBUG)

        //System.out.println("application.isRestricted ${application.isRestricted}")
    }

    /**
     * Register current device for the app within the MsgHub
     */
    fun registerDevice() {
        val answer = pushSDK.registerNewDevice(
            //mClientApiKey.value ?: "",
            //mAppFingerprint.value ?: "",
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
     * Check message queue
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
     * Get delivery report for a message
     */
    fun getMessageDeliveryReport() {
        val answer = pushSDK.getMessageDeliveryReport("message_id")
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
     * Change your device phone number
     */
    fun changeNumber() {
        val answer = pushSDK.rewriteMsisdn(userMsisdn.value.toString())
        postResponseMessage(answer.toString())
    }

    /**
     * Change URL (debug only)
     */
    fun changeUrlDebug() {
        unregisterDevice()

    }

//    /**
//     * Change your device's password
//     */
//    fun changePassword() {
//        val answer = pushSDK.rewritePassword(userPassword.value.toString())
//        postResponseMessage(answer.toString())
//    }

    /**
     * Clear the output text area
     */
    fun clearOutput() {
        postResponseMessage("Empty yet")
    }
}