package com.jetbrains.spacetutorial.cache

import com.jetbrains.spacetutorial.entity.Links
import com.jetbrains.spacetutorial.entity.Patch
import com.jetbrains.spacetutorial.entity.RocketLaunch

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun getAllLaunches(): List<RocketLaunch> {
        return dbQuery.selectAllLaunchesInfo(::mapLaunchSelecting).executeAsList()
    }

    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        patchUrlSmall: String?,
        patchUrlLarge: String?,
        articleUrl: String?
    ): RocketLaunch {
        return RocketLaunch(
            flightNumber = flightNumber.toInt(),
            missionName = missionName,
            details = details,
            launchDateUTC = launchDateUTC,
            launchSuccess = launchSuccess,
            links = Links(
                patch = Patch(
                    small = patchUrlSmall,
                    large = patchUrlLarge
                ),
                article = articleUrl
            )
        )
    }

    internal fun clearAndCreateLaunches(launches: List<RocketLaunch>) {
        dbQuery.transaction {
            dbQuery.removeAllLaunches()
            launches.forEach { launch ->
                dbQuery.insertLaunch(
                    flightNumber = launch.flightNumber.toLong(),
                    missionName = launch.missionName,
                    details = launch.details,
                    launchSuccess = launch.launchSuccess ?: false,
                    launchDateUTC = launch.launchDateUTC,
                    patchUrlSmall = launch.links.patch?.small,
                    patchUrlLarge = launch.links.patch?.large,
                    articleUrl = launch.links.article
                )
            }
        }
    }
}