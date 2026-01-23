package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import com.jetbrains.spacetutorial.context.ContextWrapper
import org.koin.core.annotation.Singleton

/**
 * Platform-specific SqlDriver factory, injected by Koin as a singleton.
 * Each platform provides its own actual implementation.
 */
@Singleton
expect fun createDatabaseDriverFactory(contextWrapper: ContextWrapper): SqlDriver