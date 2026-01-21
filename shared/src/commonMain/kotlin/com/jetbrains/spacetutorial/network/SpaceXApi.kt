package com.jetbrains.spacetutorial.network

import com.jetbrains.spacetutorial.entity.RocketLaunch
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Singleton

/**
 * Network API client for fetching SpaceX launch data.
 *
 * Uses Ktor HttpClient with JSON content negotiation to communicate
 * with the SpaceX REST API. Configured to ignore unknown JSON keys
 * for forward compatibility with API changes.
 *
 * Injected as a singleton by Koin DI to reuse the underlying HTTP connection pool.
 */
@Singleton
class SpaceXApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    /**
     * Fetches all SpaceX launches from the remote API.
     *
     * @return List of [RocketLaunch] objects from the SpaceX v5 API.
     * @throws Exception if network request fails.
     */
    suspend fun getAllLaunches(): List<RocketLaunch> {
        return httpClient.get("https://api.spacexdata.com/v5/launches").body()
    }
}