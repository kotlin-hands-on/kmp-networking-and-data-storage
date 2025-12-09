package com.jetbrains.spacetutorial.di

import com.jetbrains.spacetutorial.SpaceXSDK
import com.jetbrains.spacetutorial.cache.IOSDatabaseDriverFactory
import com.jetbrains.spacetutorial.network.SpaceXApi
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class IOSAppModule : AppModule {
    @Single
    override fun spaceApi() = SpaceXApi()

    @Single
    fun spaceXSDK(api: SpaceXApi) =
        SpaceXSDK(databaseDriverFactory = IOSDatabaseDriverFactory(), api = api)

}