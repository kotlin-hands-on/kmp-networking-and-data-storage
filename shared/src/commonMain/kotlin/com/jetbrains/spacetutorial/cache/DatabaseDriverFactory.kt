package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import com.jetbrains.spacetutorial.di.ContextWrapper
import org.koin.core.annotation.Single

// define Driver with ContextWrapper
@Single
expect class DatabaseDriverFactory(contextWrapper: ContextWrapper) {
    fun createDriver(): SqlDriver
}