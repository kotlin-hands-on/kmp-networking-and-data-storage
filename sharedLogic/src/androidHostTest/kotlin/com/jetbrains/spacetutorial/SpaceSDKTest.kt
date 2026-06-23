package com.jetbrains.spacetutorial

import app.cash.sqldelight.db.SqlDriver
import com.jetbrains.spacetutorial.cache.Database
import com.jetbrains.spacetutorial.cache.DatabaseDriverFactory
import com.jetbrains.spacetutorial.entity.Image
import com.jetbrains.spacetutorial.entity.LaunchStatus
import com.jetbrains.spacetutorial.entity.RocketLaunch
import com.jetbrains.spacetutorial.network.SpaceApi
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Android-specific unit tests for the SpaceSDK class.
 */
class SpaceSDKTest {

    // Mock dependencies
    private val mockApi = mockk<SpaceApi>()
    private val mockDatabaseDriverFactory = mockk<DatabaseDriverFactory>()
    private val mockSqlDriver = mockk<SqlDriver>()

    // Create the SDK instance with mock dependencies
    private lateinit var sdk: SpaceSDK

    // Sample data for testing
    private val sampleLaunches = listOf(
        RocketLaunch(
            id = "1",
            missionName = "Test Mission 1",
            launchDateUTC = "2023-01-01T12:00:00Z",
            image = Image(
                small = "thumbnail_url_1",
                large = "image_url_1"
            ),
            status = LaunchStatus(
                id = 3,
                name = "Successful",
                description = "Successful launch"
            )
        ),
        RocketLaunch(
            id = "2",
            missionName = "Test Mission 2",
            launchDateUTC = "2023-02-01T12:00:00Z",
            image = Image(
                small = "thumbnail_url_2",
                large = "image_url_2"
            ),
            status = LaunchStatus(
                id = 0,
                name = "Failed",
                description = "Failed launch"
            )
        )
    )

    private val sampleLaunches2 = listOf(
        RocketLaunch(
            id = "3",
            missionName = "Test Mission 3",
            launchDateUTC = "2023-01-01T12:00:00Z",
            image = Image(
                small = "thumbnail_url_3",
                large = "image_url_3"
            ),
            status = LaunchStatus(
                id = 3,
                name = "Successful",
                description = "Successful launch"
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
        sdk = SpaceSDK(mockDatabaseDriverFactory, mockApi)
    }

    /**
     * Test that getLaunches returns cached data when available and forceReload is false.
     */
    @Test
    fun testGetLaunchesFromCache() = runTest {
        // Setup: Mock the database to return cached launches
        val databaseField = SpaceSDK::class.java.getDeclaredField("database")
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
        val databaseField = SpaceSDK::class.java.getDeclaredField("database")
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
        val databaseField = SpaceSDK::class.java.getDeclaredField("database")
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
        val databaseField = SpaceSDK::class.java.getDeclaredField("database")
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