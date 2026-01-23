package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jetbrains.spacetutorial.context.ContextWrapper
import com.jetbrains.spacetutorial.di.AndroidContextWrapper
import org.koin.core.annotation.Singleton

/** Android implementation: uses AndroidSqliteDriver with the app Context. */
@Singleton
actual fun createDatabaseDriverFactory(contextWrapper: ContextWrapper): SqlDriver =
    AndroidSqliteDriver(
        AppDatabase.Schema,
        (contextWrapper as AndroidContextWrapper).context,
        "launch.db"
    )