package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jetbrains.spacetutorial.context.ContextWrapper
import org.koin.core.annotation.Singleton

/** iOS implementation: uses NativeSqliteDriver (no context needed). */
@Singleton
actual fun createDatabaseDriverFactory(contextWrapper: ContextWrapper): SqlDriver =
    NativeSqliteDriver(AppDatabase.Schema, "launch.db")