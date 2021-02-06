package com.test.newsapp.data.remote

import com.test.newsapp.data.model.News
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An interface to call API
 */
interface ApiNewsInterface{

    companion object {
        private const val API_KEY :  String = "4235f88f2ffd40b18c7d7547ed5c97b0"
    }

    @GET("top-headlines")
    fun trendingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNum: Int,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Single<News>


    /**
     * News search
     */
    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q")
        query :String = "bitcoin",
        @Query("page")
        pageNum: Int,
        @Query("apiKey")
        api_key: String = API_KEY
    ): Single<News>

}