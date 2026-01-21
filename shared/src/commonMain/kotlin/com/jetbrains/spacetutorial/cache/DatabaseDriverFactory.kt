package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import com.jetbrains.spacetutorial.context.ContextWrapper
import org.koin.core.annotation.Singleton

// define Driver with ContextWrapper
@Singleton
expect class DatabaseDriverFactory(contextWrapper: Lazy<ContextWrapper>) {
    fun createDriver(): SqlDriver
}