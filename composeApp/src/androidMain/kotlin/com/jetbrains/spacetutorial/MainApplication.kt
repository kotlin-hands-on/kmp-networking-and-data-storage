package com.jetbrains.spacetutorial

import android.app.Application
import com.jetbrains.spacetutorial.di.AndroidKoinApp

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidKoinApp.initKoin(this)
    }
}