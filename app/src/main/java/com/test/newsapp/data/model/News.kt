package com.test.newsapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class News (
    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("totalResult")
    @Expose
    var totalResult: Int = 0,

    @SerializedName("articles")
    @Expose
    var article: List<Article>
)
{
    companion object {
        fun from(newsObject: News): List<Article> {
            val articleList = mutableListOf<Article>()

            for (element in newsObject.article) {
                articleList.add(element)
            }
            return articleList
        }
    }
}