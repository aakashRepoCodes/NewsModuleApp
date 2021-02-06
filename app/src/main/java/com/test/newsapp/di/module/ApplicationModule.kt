package com.test.newsapp.di.module

import android.content.Context
import androidx.room.Room

import com.test.newsapp.di.ApplicationContext
import com.test.newsapp.di.DatabaseInfo
import com.test.newsapp.di.NetworkInfo
import com.test.newsapp.MyApplication
import com.test.newsapp.database.DatabaseService

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MyApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context = application

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String = "dummy_db"

    @Provides
    @DatabaseInfo
    fun provideDatabaseVersion(): Int = 1

    @Provides
    @NetworkInfo
    fun provideApiKey(): String = "SOME_API_KEY"

    @Singleton
    @Provides
    fun provideDatabaseInstance(): DatabaseService = Room.databaseBuilder(
        application
        , DatabaseService::class.java,
        "demo"
    ).build()

    @Singleton
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()


}
