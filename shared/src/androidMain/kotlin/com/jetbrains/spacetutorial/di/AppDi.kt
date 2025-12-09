package com.jetbrains.spacetutorial.di

import android.content.Context
import com.jetbrains.spacetutorial.RocketLaunchViewModel
import com.jetbrains.spacetutorial.SpaceXSDK
import com.jetbrains.spacetutorial.cache.AndroidDatabaseDriverFactory
import com.jetbrains.spacetutorial.network.SpaceXApi
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class AndroidAppModule : AppModule {
    @Single
    override fun spaceApi() = SpaceXApi()

    @Single
    fun spaceXSDK(api: SpaceXApi, context: Context) =
        SpaceXSDK(databaseDriverFactory = AndroidDatabaseDriverFactory(context), api = api)

    @KoinViewModel
    fun rocketLaunchViewModel(sdk: SpaceXSDK) = RocketLaunchViewModel(sdk)
}

