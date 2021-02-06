package com.test.newsapp.di.component

import com.test.newsapp.MyApplication
import com.test.newsapp.database.DatabaseService
import com.test.newsapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MyApplication)

    fun getDatabaseService(): DatabaseService

}
