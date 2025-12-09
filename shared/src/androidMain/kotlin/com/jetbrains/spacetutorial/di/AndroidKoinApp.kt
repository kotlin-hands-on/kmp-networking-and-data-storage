package com.jetbrains.spacetutorial.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinApplication
import org.koin.core.context.startKoin

@KoinApplication(modules = [AndroidAppModule::class])
object AndroidKoinApp {
    fun initKoin(context: Context){
        startKoin { androidContext(context) }
    }
}