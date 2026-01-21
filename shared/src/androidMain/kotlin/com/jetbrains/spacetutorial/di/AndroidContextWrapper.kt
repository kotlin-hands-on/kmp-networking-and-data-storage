package com.jetbrains.spacetutorial.di

import android.content.Context
import com.jetbrains.spacetutorial.context.ContextWrapper
import org.koin.core.annotation.Singleton

/**
 * Android implementation of [ContextWrapper].
 *
 * Wraps the Android application [Context] for use in common KMP code.
 * The context is provided by Koin from [MainApplication] via `androidContext()`.
 *
 * Injected as a singleton by Koin DI.
 *
 * @param context The Android application context, injected by Koin.
 */
@Singleton
class AndroidContextWrapper(val context: Context) : ContextWrapper