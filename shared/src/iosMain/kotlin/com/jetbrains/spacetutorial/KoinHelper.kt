package com.jetbrains.spacetutorial

import org.koin.ksp.generated.*
import com.jetbrains.spacetutorial.entity.RocketLaunch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.jetbrains.spacetutorial.di.IOSKoinApp

class KoinHelper : KoinComponent {
    private val sdk: SpaceXSDK by inject<SpaceXSDK>()

    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        return sdk.getLaunches(forceReload = forceReload)
    }
}

fun initKoin() {
    IOSKoinApp.startKoin()
}