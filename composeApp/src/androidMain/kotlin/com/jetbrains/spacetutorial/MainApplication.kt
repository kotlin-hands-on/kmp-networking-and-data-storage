package com.jetbrains.spacetutorial

import android.app.Application
import com.jetbrains.spacetutorial.di.KoinApp
import org.koin.android.ext.koin.androidContext
import org.koin.plugin.module.dsl.startKoin

/**
 * Android Application class responsible for initializing Koin DI.
 *
 * Koin is started here to ensure the DI container is ready before any
 * Activity or Service needs to access dependencies. The application context
 * is provided to Koin via `androidContext()`, enabling injection of
 * context-dependent components like [AndroidContextWrapper].
 *
 * Must be declared in AndroidManifest.xml:
 * ```xml
 * <application android:name=".MainApplication" ... />
 * ```
 */
class MainApplication : Application() {
    /**
     * Initializes Koin with the [KoinApp] configuration and Android context.
     */
    override fun onCreate() {
        super.onCreate()

        startKoin<KoinApp> {
            androidContext(this@MainApplication)
        }
    }
}