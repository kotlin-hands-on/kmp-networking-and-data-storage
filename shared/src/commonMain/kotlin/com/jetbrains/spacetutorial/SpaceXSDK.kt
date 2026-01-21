package com.jetbrains.spacetutorial

import com.jetbrains.spacetutorial.cache.Database
import com.jetbrains.spacetutorial.cache.DatabaseDriverFactory
import com.jetbrains.spacetutorial.entity.RocketLaunch
import com.jetbrains.spacetutorial.network.SpaceXApi
import org.koin.core.annotation.Singleton

/**
 * Main SDK for fetching SpaceX rocket launch data with local caching support.
 *
 * This class serves as the primary entry point for accessing SpaceX launch data.
 * It implements a cache-first strategy: data is served from the local database
 * when available, falling back to network requests when cache is empty or
 * a force reload is requested.
 *
 * Injected as a singleton by Koin DI to ensure consistent caching behavior
 * across the application.
 *
 * @param databaseDriverFactory Factory for creating platform-specific SQLite drivers.
 * @param api Network API client for fetching launch data from SpaceX servers.
 *
 * @see SpaceXApi for network operations
 * @see Database for local caching operations
 */
@Singleton
class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory, val api: SpaceXApi) {
    private val database = Database(databaseDriverFactory)

    /**
     * Retrieves SpaceX rocket launches using a cache-first strategy.
     *
     * Returns cached data if available and [forceReload] is false.
     * Otherwise, fetches fresh data from the network and updates the cache.
     *
     * @param forceReload If true, bypasses cache and fetches from network.
     * @return List of [RocketLaunch] objects.
     * @throws Exception if network request fails and no cached data is available.
     */
    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearAndCreateLaunches(it)
            }
        }
    }
}