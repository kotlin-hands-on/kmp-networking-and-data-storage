package com.jetbrains.spacetutorial

import android.app.Application
import com.jetbrains.spacetutorial.di.KoinApp
import org.koin.android.ext.koin.androidContext
import org.koin.ksp.generated.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // use startKoin generated in KoinApp (thanks to @KoinApplication)
        // allow to pass extra arguments like androidContext
        KoinApp.startKoin {
            androidContext(this@MainApplication)
        }
    }
}