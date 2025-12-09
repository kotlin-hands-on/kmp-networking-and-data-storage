package com.jetbrains.spacetutorial.di

import android.content.Context
import com.jetbrains.spacetutorial.RocketLaunchViewModel
import com.jetbrains.spacetutorial.SpaceXSDK
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

// Native Context Wrapper for Android
actual class ContextWrapper(val context : Context)

// Native Module to define ContextWrapper
@Module
actual class ContextModule actual constructor() {

    // Uses Scope injection to retrieve dynamically the Android context
    // Allow to have a stable Wrapper
    @Single
    actual fun providesContextWrapper(scope: Scope): ContextWrapper = ContextWrapper(scope.get())
}

@Module(includes = [CommonModule::class])
actual class NativeModule {

    @KoinViewModel
    fun rocketLaunchViewModel(sdk: SpaceXSDK) = RocketLaunchViewModel(sdk)
}