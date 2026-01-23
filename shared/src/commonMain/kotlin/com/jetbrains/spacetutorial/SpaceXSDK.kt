package com.jetbrains.spacetutorial

import app.cash.sqldelight.db.SqlDriver
import com.jetbrains.spacetutorial.cache.Database
import com.jetbrains.spacetutorial.entity.RocketLaunch
import com.jetbrains.spacetutorial.network.SpaceXApi
import org.koin.core.annotation.Singleton

@Singleton
class SpaceXSDK(dbDriver : SqlDriver, val api: SpaceXApi) {
    private val database = Database(dbDriver)

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