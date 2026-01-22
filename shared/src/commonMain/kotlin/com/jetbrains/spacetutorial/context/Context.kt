package com.jetbrains.spacetutorial.context

// Context Wrapper Pattern - allows passing platform-specific context through common KMP code
// Android: wraps Android Context | iOS: empty implementation (no context needed)
// Used by DatabaseDriverFactory for platform-specific database initialization
interface ContextWrapper