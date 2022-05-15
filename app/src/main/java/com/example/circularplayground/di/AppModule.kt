package com.example.circularplayground.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideResources(@ApplicationContext context: Context) = context.resources

    @Provides
    fun provideContext(@ApplicationContext context: Context) = context
}
