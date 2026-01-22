package com.jetbrains.spacetutorial

import com.jetbrains.spacetutorial.di.KoinApp
import com.jetbrains.spacetutorial.entity.RocketLaunch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.plugin.module.dsl.startKoin

class KoinHelper : KoinComponent {
    private val sdk: SpaceXSDK by inject()

    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        return sdk.getLaunches(forceReload = forceReload)
    }
}

fun initKoin() {
    startKoin<KoinApp>()
}