package com.jetbrains.spacetutorial.entity

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class LaunchStatus(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName(value = "description")
    val description: String
)

@Serializable
data class LaunchListResponse(
    @SerialName("results")
    val results: List<RocketLaunch>,
)

@Serializable
data class RocketLaunch(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val missionName: String,
    @SerialName("net")
    val launchDateUTC: String,
    @SerialName(value = "image")
    val image: Image,
    @SerialName(value = "status")
    val status: LaunchStatus,
) {
    var launchYear = Instant.parse(launchDateUTC).toLocalDateTime(TimeZone.UTC).year
}

@Serializable
data class Image(
    @SerialName("thumbnail_url")
    val small: String,
    @SerialName("image_url")
    val large: String,
)