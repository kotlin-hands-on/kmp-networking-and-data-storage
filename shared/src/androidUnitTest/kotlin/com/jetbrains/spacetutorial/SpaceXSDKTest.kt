package com.jetbrains.spacetutorial

import app.cash.sqldelight.db.SqlDriver
import com.jetbrains.spacetutorial.cache.Database
import com.jetbrains.spacetutorial.cache.DatabaseDriverFactory
import com.jetbrains.spacetutorial.entity.Links
import com.jetbrains.spacetutorial.entity.Patch
import com.jetbrains.spacetutorial.entity.RocketLaunch
import com.jetbrains.spacetutorial.network.SpaceXApi
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Android-specific unit tests for the SpaceXSDK class.
 */
class SpaceXSDKTest {

    // Mock dependencies
    private val mockApi = mockk<SpaceXApi>()
    private val mockDatabaseDriverFactory = mockk<DatabaseDriverFactory>()
    private val mockSqlDriver = mockk<SqlDriver>()

    // Create the SDK instance with mock dependencies
    private lateinit var sdk: SpaceXSDK

    // Sample data for testing
    private val sampleLaunches = listOf(
        RocketLaunch(
            flightNumber = 1,
            missionName = "Test Mission 1",
            launchDateUTC = "2023-01-01T12:00:00Z",
            details = "Test details 1",
            launchSuccess = true,
            links = Links(
                patch = Patch(
                    small = "small_url_1",
                    large = "large_url_1"
                ),
                article = "article_url_1"
            )
        ),
        RocketLaunch(
            flightNumber = 2,
            missionName = "Test Mission 2",
            launchDateUTC = "2023-02-01T12:00:00Z",
            details = "Test details 2",
            launchSuccess = false,
            links = Links(
                patch = Patch(
                    small = "small_url_2",
                    large = "large_url_2"
                ),
                article = "article_url_2"
            )
        )
    )

    private val sampleLaunches2 = listOf(
        RocketLaunch(
            flightNumber = 3,
            missionName = "Test Mission 3",
            launchDateUTC = "2023-01-01T12:00:00Z",
            details = "Test details 3",
            launchSuccess = true,
            links = Links(
                patch = Patch(
                    small = "small_url_3",
                    large = "large_url_3"
                ),
                article = "article_url_3"
            )
        ),
    )

    // Cache for storing launches in tests
    private val launchesCache = mutableListOf<RocketLaunch>()

    @Before
    fun setup() {
        // Clear the cache before each test
        launchesCache.clear()

        // Setup the mock database driver factory
        every { mockDatabaseDriverFactory.createDriver() } returns mockSqlDriver

        // Create a new SDK instance for each test with mocked dependencies
        sdk = SpaceXSDK(mockDatabaseDriverFactory, mockApi)
    }

    /**
     * Test that getLaunches returns cached data when available and forceReload is false.
     */
    @Test
    fun testGetLaunchesFromCache() = runTest {
        // Setup: Mock the database to return cached launches
        val databaseField = SpaceXSDK::class.java.getDeclaredField("database")
        databaseField.isAccessible = true
        val mockDatabase = mockk<Database>()
        databaseField.set(sdk, mockDatabase)

        // Mock the getAllLaunches method of the Database class
        every { mockDatabase.getAllLaunches() } returns sampleLaunches

        // Act: Call getLaunches with forceReload = false
        val result = sdk.getLaunches(forceReload = false)

        // Assert: The result should be the cached launches
        assertEquals(sampleLaunches, result)
    }

    /**
     * Test that getLaunches fetches data from the API when cache is empty.
     */
    @Test
    fun testGetLaunchesFromApiWhenCacheEmpty() = runTest {
        // Setup: Mock the database to return empty list and the API to return sample launches
        val databaseField = SpaceXSDK::class.java.getDeclaredField("database")
        databaseField.isAccessible = true
        val mockDatabase = mockk<Database>()
        databaseField.set(sdk, mockDatabase)

        // Mock the getAllLaunches method of the Database class to return an empty list
        every { mockDatabase.getAllLaunches() } returns emptyList()

        // Mock the clearAndCreateLaunches method of the Database class
        every { mockDatabase.clearAndCreateLaunches(any()) } returns Unit

        // Mock the API to return sample launches
        coEvery { mockApi.getAllLaunches() } returns sampleLaunches

        // Act: Call getLaunches with forceReload = false (but cache is empty)
        val result = sdk.getLaunches(forceReload = false)

        // Assert: The result should be the launches from the API
        assertEquals(sampleLaunches, result)
    }

    /**
     * Test that getLaunches fetches data from the API when forceReload is true.
     */
    @Test
    fun testGetLaunchesWithForceReload() = runTest {
        // Setup: Mock the database and API
        val databaseField = SpaceXSDK::class.java.getDeclaredField("database")
        databaseField.isAccessible = true
        val mockDatabase = mockk<Database>()
        databaseField.set(sdk, mockDatabase)

        // Mock the getAllLaunches method of the Database class to return cached launches
        every { mockDatabase.getAllLaunches() } returns sampleLaunches

        // Mock the clearAndCreateLaunches method of the Database class
        every { mockDatabase.clearAndCreateLaunches(any()) } returns Unit

        // Mock the API to return sample launches
        coEvery { mockApi.getAllLaunches() } returns sampleLaunches2

        // Act: Call getLaunches with forceReload = true
        val result = sdk.getLaunches(forceReload = true)

        // Assert: The result should be the launches from the API
        assertEquals(sampleLaunches2, result)
    }

    /**
     * Test that getLaunches throws an exception when the API call fails.
     */
    @Test
    fun testGetLaunchesException() = runTest {
        // Setup: Mock the database and API
        val databaseField = SpaceXSDK::class.java.getDeclaredField("database")
        databaseField.isAccessible = true
        val mockDatabase = mockk<Database>()
        databaseField.set(sdk, mockDatabase)

        // Mock the getAllLaunches method of the Database class to return an empty list
        every { mockDatabase.getAllLaunches() } returns emptyList()

        // Mock the API to throw an exception
        coEvery { mockApi.getAllLaunches() } throws Exception("API error")

        // Act & Assert: Call getLaunches should throw an exception
        val exception = assertFailsWith<Exception> {
            sdk.getLaunches(forceReload = true)
        }

        // Verify the exception message
        assertEquals("API error", exception.message)
    }
}