package com.push.android.pushsdkandroidpr.utils

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.push.android.pushsdkandroidpr.MainActivityViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val application: Application, private val preferences: SharedPreferences):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(application, preferences) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}