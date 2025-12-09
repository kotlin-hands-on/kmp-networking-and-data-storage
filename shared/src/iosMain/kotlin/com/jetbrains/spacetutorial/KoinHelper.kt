package com.jetbrains.spacetutorial

import com.jetbrains.spacetutorial.di.KoinApp
import org.koin.ksp.generated.*
import com.jetbrains.spacetutorial.entity.RocketLaunch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinHelper : KoinComponent {
    private val sdk: SpaceXSDK by inject()

    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        return sdk.getLaunches(forceReload = forceReload)
    }
}

fun initKoin() {
    KoinApp.startKoin()
}