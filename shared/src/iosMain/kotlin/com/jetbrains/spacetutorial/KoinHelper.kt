package com.jetbrains.spacetutorial

import com.jetbrains.spacetutorial.di.KoinApp
import com.jetbrains.spacetutorial.entity.RocketLaunch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.plugin.module.dsl.startKoin

/**
 * iOS helper class for accessing Koin-injected dependencies.
 *
 * Implements [KoinComponent] to enable service locator pattern access to
 * the DI container from Swift code. This bridges the gap between Koin's
 * constructor injection (not available in Swift) and iOS's need for
 * explicit dependency access.
 *
 * Usage from Swift:
 * ```swift
 * let helper = KoinHelper()
 * let launches = try await helper.getLaunches(forceReload: true)
 * ```
 */
class KoinHelper : KoinComponent {
    private val sdk: SpaceXSDK by inject()

    /**
     * Fetches SpaceX rocket launches.
     *
     * @param forceReload If true, bypasses cache and fetches fresh data from network.
     * @return List of [RocketLaunch] objects.
     */
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        return sdk.getLaunches(forceReload = forceReload)
    }
}

/**
 * Initializes Koin dependency injection for iOS.
 *
 * Must be called once during iOS app startup, typically in the
 * SwiftUI App's initializer or AppDelegate.
 *
 * Usage from Swift:
 * ```swift
 * init() {
 *     KoinHelperKt.doInitKoin()
 * }
 * ```
 */
fun initKoin() {
    startKoin<KoinApp>()
}