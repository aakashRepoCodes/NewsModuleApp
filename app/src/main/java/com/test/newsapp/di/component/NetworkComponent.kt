package com.test.newsapp.di.component

import com.test.newsapp.data.remote.NewsRepository
import com.test.newsapp.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    fun inject(newsRepository: NewsRepository)

}