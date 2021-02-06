package com.test.newsapp

import android.app.Application
import com.test.newsapp.di.component.ApplicationComponent
import com.test.newsapp.di.component.DaggerApplicationComponent
import com.test.newsapp.di.module.ApplicationModule

class MyApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        getDependencies()
    }

    private fun getDependencies() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }
}