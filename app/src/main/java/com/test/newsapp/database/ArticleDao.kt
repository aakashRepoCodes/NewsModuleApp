package com.test.newsapp.database

import androidx.room.*
import com.test.newsapp.data.model.Article
import io.reactivex.Single

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsArticle(article: Article) : Single<Long>

    @Query("SELECT * FROM article")
    fun getNewsArticles(): Single<List<Article>>

    @Query("SELECT * FROM article WHERE bookmark =:value")
    fun getBookMarkedArticles(value : Boolean): Single<List<Article>>

    @Delete
    fun deleteNewsArticle(article: Article)

    @Query("UPDATE article SET bookmark = :isArticleBookMarked WHERE id =:articleId")
    fun saveOrRemoveBookmarkArticle(isArticleBookMarked: Boolean, articleId : Long) : Single<Int>

    @Query("SELECT * FROM article WHERE title =:query")
    fun searchArticle(query : String) : Single<List<Article>>

    @Query("SELECT count(*) from article")
    fun count(): Single<Int>

    @Query("SELECT * FROM article WHERE title like :title")
    fun getSingleArticle(title : String) : Single<Article>

}