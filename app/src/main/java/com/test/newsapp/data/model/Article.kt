package com.test.newsapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Time

@Entity(tableName = "article")
data class Article(

    @PrimaryKey
    @SerializedName("none")
    @Expose(serialize = false)
    var id : Long = System.currentTimeMillis(),

    @ColumnInfo(name = "author")
    val author: String?,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String,

    @ColumnInfo(name = "bookmark")
    val isBookMarked: Boolean = false

)