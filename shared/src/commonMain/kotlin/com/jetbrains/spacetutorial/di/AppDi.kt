package com.jetbrains.spacetutorial.di

import com.jetbrains.spacetutorial.network.SpaceXApi

interface AppModule {
    fun spaceApi(): SpaceXApi
}