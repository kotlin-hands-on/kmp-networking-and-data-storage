package com.jetbrains.spacetutorial.context

/**
 * Platform-agnostic interface for wrapping native platform contexts.
 *
 * This interface enables passing platform-specific context objects (like Android's
 * [Context]) through the common Kotlin Multiplatform code without exposing
 * platform-specific types.
 *
 * Each platform provides its own implementation:
 * - Android: [com.jetbrains.spacetutorial.di.AndroidContextWrapper] wrapping Android [Context]
 * - iOS: [IOSContextWrapper] (empty implementation as iOS doesn't require a context)
 *
 * Used primarily by [com.jetbrains.spacetutorial.cache.DatabaseDriverFactory] to obtain
 * platform context for database initialization.
 */
interface ContextWrapper