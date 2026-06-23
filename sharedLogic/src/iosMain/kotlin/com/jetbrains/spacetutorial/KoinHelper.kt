package com.jetbrains.spacetutorial

import com.jetbrains.spacetutorial.cache.IOSDatabaseDriverFactory
import org.koin.core.component.KoinComponent
import com.jetbrains.spacetutorial.entity.RocketLaunch
import com.jetbrains.spacetutorial.network.SpaceApi
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinHelper : KoinComponent {
    private val sdk: SpaceSDK by inject<SpaceSDK>()

    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        return sdk.getLaunches(forceReload = forceReload)
    }
}

fun initKoin() {
    startKoin {
        modules(module {
            single<SpaceApi> { SpaceApi() }
            single<SpaceSDK> {
                SpaceSDK(
                    databaseDriverFactory = IOSDatabaseDriverFactory(), api = get()
                )
            }
        })
    }
}

