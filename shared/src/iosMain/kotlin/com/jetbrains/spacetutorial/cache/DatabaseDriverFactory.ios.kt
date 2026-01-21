package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jetbrains.spacetutorial.context.ContextWrapper
import org.koin.core.annotation.Singleton

/**
 * iOS implementation of [DatabaseDriverFactory].
 *
 * Creates a [NativeSqliteDriver] for iOS/macOS platforms. Unlike Android,
 * iOS does not require a context for database creation, so the [contextWrapper]
 * parameter is unused but required for interface compatibility.
 *
 * Injected as a singleton by Koin DI.
 *
 * @param contextWrapper Unused on iOS; required for expect/actual compatibility.
 */
@Singleton
actual class DatabaseDriverFactory actual constructor(contextWrapper: Lazy<ContextWrapper>) {
    /**
     * Creates an iOS-specific native SQLite driver.
     *
     * @return [NativeSqliteDriver] configured for the "launch.db" database.
     */
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(AppDatabase.Schema, "launch.db")
}