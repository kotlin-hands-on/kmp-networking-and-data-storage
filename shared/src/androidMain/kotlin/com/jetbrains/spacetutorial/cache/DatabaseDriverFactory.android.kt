package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jetbrains.spacetutorial.di.ContextWrapper
import org.koin.core.annotation.Single

// Singleton -
// Uses ContextWrapper to inject Android context
@Single
actual class DatabaseDriverFactory actual constructor(val contextWrapper: ContextWrapper) {
    actual fun createDriver(): SqlDriver {
        // use of Android context
        return AndroidSqliteDriver(AppDatabase.Schema, contextWrapper.context, "launch.db")
    }
}