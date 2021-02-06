package com.test.newsapp.data.remote

import com.test.newsapp.data.model.Article
import com.test.newsapp.data.model.News
import com.test.newsapp.di.component.DaggerNetworkComponent
import com.test.newsapp.di.module.NetworkModule
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * This class makes the network calls to get list of pokemons.
 */
class NewsRepository {

    @Inject
    lateinit var networkModule: NetworkModule

    @Inject
    lateinit var retrofit: Retrofit

    init {
        DaggerNetworkComponent.builder().build().inject(this@NewsRepository)
    }

    fun getNews(): Single<List<Article>> {
        return networkModule.provideRetrofitService(retrofit)
            .trendingNews ("in", 1)
            .subscribeOn(Schedulers.io())
            .map {
                News.from(it)
            }
    }

    fun searchNews(): Single<List<Article>> {
        return networkModule.provideRetrofitService(retrofit)
            .trendingNews ("in", 1)
            .subscribeOn(Schedulers.io())
            .map {
                News.from(it)
            }
    }
}

