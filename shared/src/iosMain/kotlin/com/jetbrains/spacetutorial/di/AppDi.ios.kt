package com.jetbrains.spacetutorial.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

actual class ContextWrapper()

@Module
actual class ContextModule actual constructor() {

    @Single
    actual fun providesContextWrapper(scope: Scope): ContextWrapper = ContextWrapper()
}

@Module
@ComponentScan(" com.jetbrains.spacetutorial")
actual class NativeModule