package com.demo.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DemoApp : Application() {

    companion object {
        lateinit var instance: DemoApp
        private const val TAG = "ApplicationClass"

        @JvmName("getInstance1")
        fun getInstance(): DemoApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}