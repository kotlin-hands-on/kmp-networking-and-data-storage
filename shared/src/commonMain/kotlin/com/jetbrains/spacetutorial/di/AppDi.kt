package com.jetbrains.spacetutorial.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module

// Koin Module using Koin Compiler Plugin annotations
// @ComponentScan auto-discovers all @Singleton, @KoinViewModel annotated classes in the package
@Configuration
@ComponentScan("com.jetbrains.spacetutorial")
@Module
class AppModule

// Koin Application entry point - referenced by startKoin<KoinApp> on Android & iOS
@KoinApplication
object KoinApp