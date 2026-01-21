package com.jetbrains.spacetutorial.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jetbrains.spacetutorial.context.ContextWrapper
import com.jetbrains.spacetutorial.di.AndroidContextWrapper
import org.koin.core.annotation.Singleton

@Singleton
actual class DatabaseDriverFactory actual constructor(val contextWrapper: Lazy<ContextWrapper>) {

    private val androidContext by lazy { (contextWrapper.value as AndroidContextWrapper).context }

    actual fun createDriver(): SqlDriver {
        // use of Android context
        return AndroidSqliteDriver(AppDatabase.Schema, androidContext, "launch.db")
    }
}