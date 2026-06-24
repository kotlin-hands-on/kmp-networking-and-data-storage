package com.jetbrains.spacetutorial.cache

import com.jetbrains.spacetutorial.entity.Image
import com.jetbrains.spacetutorial.entity.LaunchStatus
import com.jetbrains.spacetutorial.entity.RocketLaunch

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun getAllLaunches(): List<RocketLaunch> {
        return dbQuery.selectAllLaunchesInfo(::mapLaunchSelecting).executeAsList()
    }

    private fun mapLaunchSelecting(
        flightNumber: String,
        missionName: String,
        launchDateUTC: String,
        imageSmall: String,
        imageLarge: String,
        statusId: Long,
        statusName: String,
        statusDescription: String
    ): RocketLaunch {
        return RocketLaunch(
            id = flightNumber,
            missionName = missionName,
            launchDateUTC = launchDateUTC,
            image = Image(
                small = imageSmall,
                large = imageLarge
            ),
            status = LaunchStatus(
                id = statusId.toInt(),
                name = statusName,
                description = statusDescription
            )
        )
    }

    internal fun clearAndCreateLaunches(launches: List<RocketLaunch>) {
        dbQuery.transaction {
            dbQuery.removeAllLaunches()
            launches.forEach { launch ->
                dbQuery.insertLaunch(
                    flightNumber = launch.id,
                    missionName = launch.missionName,
                    launchDateUTC = launch.launchDateUTC,
                    imageSmall = launch.image.small,
                    imageLarge = launch.image.large,
                    statusId = launch.status.id.toLong(),
                    statusName = launch.status.name,
                    statusDescription = launch.status.description,
                )
            }
        }
    }
}