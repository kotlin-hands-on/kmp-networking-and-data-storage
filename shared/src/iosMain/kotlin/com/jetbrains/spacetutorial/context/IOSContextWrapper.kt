package com.jetbrains.spacetutorial.context

import org.koin.core.annotation.Singleton

/**
 * iOS implementation of [ContextWrapper].
 *
 * Empty implementation since iOS doesn't require a context object for
 * database or other platform operations. Exists to satisfy the
 * [ContextWrapper] interface requirement in common code.
 *
 * Injected as a singleton by Koin DI.
 */
@Singleton
class IOSContextWrapper : ContextWrapper