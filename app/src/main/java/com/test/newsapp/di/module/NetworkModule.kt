package com.test.newsapp.di.module

import com.test.newsapp.data.remote.ApiNewsInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule{

    val BASE_URL = "https://newsapi.org/v2/"

    @Singleton
    @Provides
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofitService(retrofit: Retrofit): ApiNewsInterface {
        return retrofit.create(ApiNewsInterface::class.java)
    }

    @Singleton
    @Provides
    internal fun provideNetworkModule(): NetworkModule {
        return this
    }

    @Provides
    internal fun provideBaseUrl(): String {
        return BASE_URL
    }
}