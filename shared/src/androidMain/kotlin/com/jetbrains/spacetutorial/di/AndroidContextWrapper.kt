package com.jetbrains.spacetutorial.di

import android.content.Context
import com.jetbrains.spacetutorial.context.ContextWrapper
import org.koin.core.annotation.Singleton

@Singleton
class AndroidContextWrapper(val context : Context) : ContextWrapper