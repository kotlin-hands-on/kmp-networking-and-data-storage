package com.jetbrains.spacetutorial

import com.jetbrains.spacetutorial.cache.AndroidDatabaseDriverFactory
import com.jetbrains.spacetutorial.network.SpaceXApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SpaceXApi> { SpaceXApi() }
    single<SpaceXSDK> {
        SpaceXSDK(
            databaseDriverFactory = AndroidDatabaseDriverFactory(
                androidContext()
            ), api = get()
        )
    }
    viewModel { RocketLaunchViewModel(sdk = get()) }
}