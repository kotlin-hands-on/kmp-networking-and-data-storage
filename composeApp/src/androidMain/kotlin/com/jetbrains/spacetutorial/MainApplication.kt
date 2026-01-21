package com.jetbrains.spacetutorial

import android.app.Application
import com.jetbrains.spacetutorial.di.KoinApp
import org.koin.android.ext.koin.androidContext
import org.koin.plugin.module.dsl.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin<KoinApp> {
            androidContext(this@MainApplication)
        }
    }
}