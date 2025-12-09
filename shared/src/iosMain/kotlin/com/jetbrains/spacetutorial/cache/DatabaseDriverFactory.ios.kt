package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jetbrains.spacetutorial.di.ContextWrapper
import org.koin.core.annotation.Single

@Single
actual class DatabaseDriverFactory actual constructor(contextWrapper: ContextWrapper) {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(AppDatabase.Schema, "launch.db")
}