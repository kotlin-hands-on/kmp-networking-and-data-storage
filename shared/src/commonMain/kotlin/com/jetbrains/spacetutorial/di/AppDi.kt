package com.jetbrains.spacetutorial.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module

/**
 * Main Koin DI module configuration for the application.
 *
 * This module uses the Koin Compiler Plugin for compile-time dependency resolution.
 * The [@ComponentScan] annotation automatically discovers all classes annotated with
 * [@Singleton], [@KoinViewModel], and other Koin annotations within the specified package.
 *
 * @see org.koin.core.annotation.Singleton for singleton-scoped dependencies
 * @see org.koin.core.annotation.KoinViewModel for Android ViewModel injection
 */
@Configuration
@ComponentScan("com.jetbrains.spacetutorial")
@Module
class AppModule

/**
 * Koin application entry point.
 *
 * This object serves as the root configuration for Koin dependency injection.
 * It is referenced when initializing Koin on each platform:
 * - Android: `startKoin<KoinApp> { androidContext(this) }` in [MainApplication]
 * - iOS: `startKoin<KoinApp>()` via [initKoin] function
 *
 * The Koin Compiler Plugin generates the necessary wiring code at compile-time,
 * eliminating runtime reflection overhead.
 */
@KoinApplication
object KoinApp