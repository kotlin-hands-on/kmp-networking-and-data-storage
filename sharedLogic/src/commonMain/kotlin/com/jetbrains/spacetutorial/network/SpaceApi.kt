package com.jetbrains.spacetutorial.network

import com.jetbrains.spacetutorial.entity.LaunchListResponse
import com.jetbrains.spacetutorial.entity.RocketLaunch
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SpaceApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getAllLaunches(): List<RocketLaunch> {
        return (httpClient.get("https://lldev.thespacedevs.com/2.3.0/launches/previous/?mode=list&format=json").body() as LaunchListResponse).results
    }
}