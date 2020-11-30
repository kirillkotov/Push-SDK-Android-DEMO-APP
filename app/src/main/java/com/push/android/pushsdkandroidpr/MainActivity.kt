package com.push.android.pushsdkandroidpr

import android.app.Application
import android.content.*
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.push.android.pushsdkandroidpr.databinding.ActivityMainBinding
import com.push.android.pushsdkandroid.PushSDK
import com.push.android.pushsdkandroid.core.ApiParams
import com.push.android.pushsdkandroidpr.utils.ViewModelFactory

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /**
     * BroadcastReceiver to receive PushSDK message broadcasts
     */
    private val mPlugInReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                PushSDK.BROADCAST_QUEUE_INTENT_ACTION -> {
                    intent.extras?.let {
                        binding.myViewModel?.apply {
                            postResponseMessage(it.getString(PushSDK.BROADCAST_QUEUE_EXTRA_NAME).toString())
                        }
                    }
                }
                PushSDK.BROADCAST_PUSH_DATA_INTENT_ACTION -> {
                    intent.extras?.let {
                        binding.myViewModel?.apply {
                            postResponseMessage(it.getString(PushSDK.BROADCAST_PUSH_DATA_EXTRA_NAME).toString())
                        }
                    }
                }
            }
        }
    }

    /**
     * Check intent to find out if user clicked the notification
     */
    private fun checkIntent(intent: Intent?) {
        intent?.let {
            if (it.action == PushSDK.NOTIFICATION_CLICK_INTENT_ACTION) {
                binding.myViewModel?.apply {
                    it.extras?.let {extras ->
                        postResponseMessage(extras.getString(PushSDK.NOTIFICATION_CLICK_PUSH_DATA_EXTRA_NAME, "empty"))
                        this@MainActivity.intent = null
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences: SharedPreferences = getSharedPreferences("demo", Context.MODE_PRIVATE)
        val factory = ViewModelFactory(application, preferences)
        val mainActivityViewModel =
            ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.myViewModel = mainActivityViewModel
        binding.lifecycleOwner = this

        //Observe the output and log it
        mainActivityViewModel.responseMessages.observe(this, Observer {
            Log.d("responseMessages", it)
        })

        //Observe and handle events
        mainActivityViewModel.systemMessages.observe(this, Observer {event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    "initSDK" -> {
                        binding.credentials.visibility = View.GONE
                    }
                    "no_credentials" -> {
                        Snackbar.make(
                            binding.getRoot(),
                            "Please provide credentials",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        //register broadcast receiver
        val filter = IntentFilter()
        filter.addAction(PushSDK.BROADCAST_PUSH_DATA_INTENT_ACTION)
        filter.addAction(PushSDK.BROADCAST_QUEUE_INTENT_ACTION)
        registerReceiver(mPlugInReceiver, filter)
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
        checkIntent(intent)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        //unregister broadcast receiver
        unregisterReceiver(mPlugInReceiver)
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

