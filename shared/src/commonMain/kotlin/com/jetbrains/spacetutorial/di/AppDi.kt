package com.jetbrains.spacetutorial.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module

@Configuration
@ComponentScan("com.jetbrains.spacetutorial")
@Module
class AppModule

@KoinApplication
object KoinApp