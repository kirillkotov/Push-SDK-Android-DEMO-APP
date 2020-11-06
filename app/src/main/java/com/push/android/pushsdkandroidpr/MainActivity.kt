package com.push.android.pushsdkandroidpr

import androidx.appcompat.app.AppCompatActivity

import android.util.Log
import android.annotation.SuppressLint
import android.content.*
import android.widget.Button
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ActivityCompat

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import kotlin.properties.Delegates


import com.push.android.pushsdkandroid.core.PushKFunAnswerRegister
import com.push.android.pushsdkandroid.core.PushKFunAnswerGeneral
import com.push.android.pushsdkandroid.PushKPushMess

import android.os.Bundle
import com.google.firebase.iid.FirebaseInstanceId
import com.push.android.pushsdkandroid.PushSDK
import com.push.android.pushsdkandroid.core.PushSdkParametersPublic

object MessBuffer {
    var buff: String? = ""
}

class ForegroundBackgroundListener(textBox: EditText) : LifecycleObserver {

    //any classes initialization
    private var textBox: EditText by Delegates.notNull()
    private val logTag: String = "ApplicationLog"


    //main class initialization
    init {
        this.textBox = textBox
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startSomething() {
        Log.v(logTag, "APP IS ON FOREGROUND")

        PushKPushMess.message?.let {
            MessBuffer.buff = "${MessBuffer.buff} \n ${PushKPushMess.message}"
            textBox.setText(MessBuffer.buff)
            PushKPushMess.message = null
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopSomething() {
        Log.v("ProcessLog", "APP IS IN BACKGROUND")
    }
}

class MainActivity : AppCompatActivity() {

    private val mPlugInReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            PushKPushMess.message?.let {
                val edtName: EditText = findViewById(R.id.editText2)
                MessBuffer.buff = "${MessBuffer.buff} \n ${PushKPushMess.message}"
                edtName.setText(MessBuffer.buff)
                PushKPushMess.message = null
            }
        }
    }

    private val apiServerKey = "test"
    private val apiFingerprint = "test_fingerprint"

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter()
        filter.addAction("com.push.android.pushsdkandroid.Push")
        registerReceiver(mPlugInReceiver, filter)
        //val hPlatformPushAdapterSdk = HyberSDK(context = this, platform_branch = PushSdkParametersPublic.branchTestValue, log_level = "debug")
        //hPlatformPushAdapterSdk.hyber_check_queue()
    }

    override fun onResume() {
        super.onResume()
        println("Resumed App")
        //val hPlatformPushAdapterSdk = HyberSDK(this, PushSdkParametersPublic.branchTestValue, "debug")
        //hPlatformPushAdapterSdk.hyber_check_queue()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(mPlugInReceiver)
        //hPlatformPushAdapterSdk.hyber_check_queue()
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hPlatformPushAdapterSdk =
            PushSDK(
                context = this,
                //platform_branch = PushSdkParametersPublic.branchMasterValue,
                log_level = "debug",
                push_style = 1,
                basePushURL = "https://example.com/push/{version}"
            )

        val permissions = arrayOf(android.Manifest.permission.READ_PHONE_STATE)
        ActivityCompat.requestPermissions(this, permissions, 0)

        //different form elements
        val button1: Button = findViewById(R.id.button)
        val button2: Button = findViewById(R.id.button2)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)    //message_callback
        val button8: Button = findViewById(R.id.button8)    //delivery_report
        val button9: Button = findViewById(R.id.button9)    //hyber_check_queue
        val button11: Button = findViewById(R.id.button11)  //Clear_textbox
        val button12: Button = findViewById(R.id.button12)
        val textBoxForResultPrinting: EditText = findViewById(R.id.editText2)
        val phoneNumberField: EditText = findViewById(R.id.editText)
        textBoxForResultPrinting.setSelection(0)

        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(
                ForegroundBackgroundListener(textBoxForResultPrinting)
            )


        button1.setOnClickListener {
            val phoneNumber: String = phoneNumberField.text.toString()
            println(phoneNumber)
            hPlatformPushAdapterSdk.rewrite_msisdn(phoneNumber)
            val registrationTest: PushKFunAnswerRegister =
                hPlatformPushAdapterSdk.push_register_new(
                    apiServerKey,
                    apiFingerprint,
                    phoneNumberField.text.toString(),
                    "password"
                )
            MessBuffer.buff = "${MessBuffer.buff} \n $registrationTest"
            textBoxForResultPrinting.setText(MessBuffer.buff)
        }


        button2.setOnClickListener {
            val sdkAnswer2: PushKFunAnswerGeneral = hPlatformPushAdapterSdk.push_clear_current_device()
            MessBuffer.buff = "${MessBuffer.buff} \n $sdkAnswer2"
            textBoxForResultPrinting.setText(MessBuffer.buff)
        }


        button4.setOnClickListener {
            println("Timestamp current: ${System.currentTimeMillis()}")
            val sdkAnswer4: PushKFunAnswerGeneral =
                hPlatformPushAdapterSdk.push_get_message_history(7200)
            MessBuffer.buff = "${MessBuffer.buff} \n $sdkAnswer4"
            textBoxForResultPrinting.setText(MessBuffer.buff)
        }

        button5.setOnClickListener {
            val sdkAnswer5: PushKFunAnswerGeneral =
                hPlatformPushAdapterSdk.push_get_device_all_from_server()
            MessBuffer.buff = "${MessBuffer.buff} \n $sdkAnswer5"
            textBoxForResultPrinting.setText(MessBuffer.buff)
        }

        button6.setOnClickListener {
            val sdkAnswer: PushKFunAnswerGeneral =
                hPlatformPushAdapterSdk.push_update_registration()
            MessBuffer.buff = "${MessBuffer.buff} \n $sdkAnswer"
            textBoxForResultPrinting.setText(MessBuffer.buff)
        }

        button7.setOnClickListener {
            val sdkAnswer7: PushKFunAnswerGeneral =
                hPlatformPushAdapterSdk.push_send_message_callback("test_mess_id", "Hello World")
            MessBuffer.buff = "${MessBuffer.buff} \n $sdkAnswer7"
            textBoxForResultPrinting.setText(MessBuffer.buff)
        }

        button8.setOnClickListener {
            val sdkAnswer8: PushKFunAnswerGeneral =
                hPlatformPushAdapterSdk.push_message_delivery_report("test_mess_id")
            MessBuffer.buff = "${MessBuffer.buff} \n $sdkAnswer8"
            textBoxForResultPrinting.setText(MessBuffer.buff)
        }

        //hyber_check_queue
        button9.setOnClickListener {
            val sdkAnswer9: PushKFunAnswerGeneral =
                hPlatformPushAdapterSdk.push_check_queue()
            MessBuffer.buff = "${MessBuffer.buff} \n $sdkAnswer9"
            textBoxForResultPrinting.setText(MessBuffer.buff)
        }

        //clear all
        button10.setOnClickListener {
            val sdkAnswer10: PushKFunAnswerGeneral = hPlatformPushAdapterSdk.push_clear_all_device()
            MessBuffer.buff = "${MessBuffer.buff} \n $sdkAnswer10"
            textBoxForResultPrinting.setText(MessBuffer.buff)
        }

        button11.setOnClickListener {
            textBoxForResultPrinting.setText("")
            PushKPushMess.message = ""
            MessBuffer.buff = ""
        }


        button12.setOnClickListener {

            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
                val token = instanceIdResult.token
                if (token != "") {
                    println(token)
                }
            }
        }
    }

}

