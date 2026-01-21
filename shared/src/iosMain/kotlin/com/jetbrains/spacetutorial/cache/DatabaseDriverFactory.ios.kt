package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jetbrains.spacetutorial.context.ContextWrapper
import org.koin.core.annotation.Singleton

@Singleton
actual class DatabaseDriverFactory actual constructor(contextWrapper: Lazy<ContextWrapper>) {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(AppDatabase.Schema, "launch.db")
}