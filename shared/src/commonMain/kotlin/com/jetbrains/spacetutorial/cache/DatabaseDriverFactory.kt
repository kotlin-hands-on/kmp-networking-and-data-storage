package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import com.jetbrains.spacetutorial.context.ContextWrapper
import org.koin.core.annotation.Singleton

// Expect/actual pattern for platform-specific SQLite driver creation
// @Singleton: Koin creates one instance shared across the app
// Lazy<ContextWrapper>: defers context resolution to avoid initialization order issues with Koin
@Singleton
expect class DatabaseDriverFactory(contextWrapper: Lazy<ContextWrapper>) {
    fun createDriver(): SqlDriver
}