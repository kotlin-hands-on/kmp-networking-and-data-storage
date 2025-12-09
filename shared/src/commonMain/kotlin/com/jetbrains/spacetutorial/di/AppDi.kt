package com.jetbrains.spacetutorial.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

// Context Wrapper Pattern, to allow pass native context as KMP
expect class ContextWrapper

// Define Context Wrapper
@Module
expect class ContextModule() {

    @Single
    fun providesContextWrapper(scope : Scope) : ContextWrapper
}

// Gather all common KMP components
@Module(includes = [ContextModule::class])
@ComponentScan("com.jetbrains.spacetutorial")
class CommonModule

// Native module, to give the hand back for native Components (like ViewModel on Android)
@Module(includes = [CommonModule::class])
expect class NativeModule()

// Main Module, uses includes
// KSP2 is introducing an issue and prevent any @Configuration scanning
// need to specify modules by hand
@Module(includes = [CommonModule::class, NativeModule::class])
class AppModule

// Main Koin Entry point
// includes modules to start
// KSP2 is introducing an issue and prevent any @Configuration scanning
@KoinApplication(modules = [AppModule::class])
object KoinApp