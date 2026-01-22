package com.jetbrains.spacetutorial

import com.jetbrains.spacetutorial.di.KoinApp
import com.jetbrains.spacetutorial.entity.RocketLaunch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.plugin.module.dsl.startKoin

// iOS helper class - implements KoinComponent to access Koin DI from Swift
// Bridges Koin's constructor injection (not available in Swift) to iOS
class KoinHelper : KoinComponent {
    // Use inject() delegate to get SpaceXSDK from Koin container
    private val sdk: SpaceXSDK by inject()

    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        return sdk.getLaunches(forceReload = forceReload)
    }
}

// iOS Koin initialization - call from Swift: KoinHelperKt.doInitKoin()
fun initKoin() {
    startKoin<KoinApp>()
}