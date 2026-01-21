package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jetbrains.spacetutorial.context.ContextWrapper
import com.jetbrains.spacetutorial.di.AndroidContextWrapper
import org.koin.core.annotation.Singleton

/**
 * Android implementation of [DatabaseDriverFactory].
 *
 * Creates an [AndroidSqliteDriver] using the Android [Context] obtained from
 * [AndroidContextWrapper]. The context is lazily retrieved to ensure proper
 * initialization order with Koin DI.
 *
 * Injected as a singleton by Koin DI.
 *
 * @param contextWrapper Lazy-initialized wrapper containing the Android application context.
 */
@Singleton
actual class DatabaseDriverFactory actual constructor(val contextWrapper: Lazy<ContextWrapper>) {

    private val androidContext by lazy { (contextWrapper.value as AndroidContextWrapper).context }

    /**
     * Creates an Android-specific SQLite driver.
     *
     * @return [AndroidSqliteDriver] configured for the "launch.db" database.
     */
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, androidContext, "launch.db")
    }
}