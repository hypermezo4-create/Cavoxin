package com.deadzon.app.app

import android.content.Context
import com.deadzon.app.core.root.RootShell
import com.deadzon.app.core.root.SuRootShell
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RootModule {
    @Binds
    abstract fun bindRootShell(impl: SuRootShell): RootShell
}
