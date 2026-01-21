package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import com.jetbrains.spacetutorial.context.ContextWrapper
import org.koin.core.annotation.Singleton

/**
 * Platform-specific factory for creating SQLDelight database drivers.
 *
 * This expect class defines the contract for creating [SqlDriver] instances
 * across different platforms (Android, iOS). Each platform provides its own
 * actual implementation using the appropriate SQLite driver.
 *
 * Uses [Lazy] wrapper for [ContextWrapper] to defer platform context initialization
 * until the driver is actually needed, avoiding initialization order issues.
 *
 * Injected as a singleton by Koin DI.
 *
 * @param contextWrapper Lazy-initialized platform-specific context wrapper.
 *
 * @see com.jetbrains.spacetutorial.di.AndroidContextWrapper for Android implementation
 * @see com.jetbrains.spacetutorial.context.IOSContextWrapper for iOS implementation
 */
@Singleton
expect class DatabaseDriverFactory(contextWrapper: Lazy<ContextWrapper>) {
    /**
     * Creates a platform-specific SQLite driver for the application database.
     *
     * @return [SqlDriver] configured for the "launch.db" database.
     */
    fun createDriver(): SqlDriver
}