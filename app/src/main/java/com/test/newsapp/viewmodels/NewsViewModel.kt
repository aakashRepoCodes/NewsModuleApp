package com.test.newsapp.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.newsapp.di.ActivityScope
import com.test.newsapp.data.model.Article
import com.test.newsapp.data.remote.NewsRepository
import com.test.newsapp.database.DatabaseService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class NewsViewModel @Inject constructor(
    private val databaseService: DatabaseService,
    private val newsRepository: NewsRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val _newsLiveData = MutableLiveData<List<Article>>()
    val newsLiveData: LiveData<List<Article>>
        get() = _newsLiveData

    private val _newsFailedLiveData = MutableLiveData<Boolean>()
    val newsFailedLiveData: LiveData<Boolean>
        get() = _newsFailedLiveData

    private val _newsLocalLiveData = MutableLiveData<List<Article>>()
    val newsLocalLiveData: LiveData<List<Article>>
        get() = _newsLocalLiveData


     fun readNews() {
        compositeDisposable.add(
            newsRepository.getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _newsLiveData.value = it
                    insertFetchedArticles(it)
                }, {
                    _newsFailedLiveData.value = true
                })
        )
    }

    fun readNewsFromDB() {
        compositeDisposable.add(
            databaseService.getArticleDao().getNewsArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _newsLocalLiveData.value = it
                }, {
                })
        )
    }


    private fun insertFetchedArticles(list: List<Article>) {
        for (i in list.indices) {
            val id : Long = System.currentTimeMillis()
            list[i].id = id
            insertItem(list[i])
        }
    }

    private fun insertItem(article: Article) {
        compositeDisposable.add(
            databaseService.getArticleDao().insertNewsArticle(article)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.e("value inserted", article.title)
                }, {
                    Log.e("value failed to insert", article.title)
                })
        )
    }

    fun bookMarkArticle(article: Article, isfav: Boolean) {
        compositeDisposable.add(
            databaseService.getArticleDao().saveOrRemoveBookmarkArticle(isfav, article.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it is Int) {
                        Log.e("value updated success ", article.title)
                    } else
                        Log.e("value updated failed ", article.title)

                }, {
                    Log.e("value failed to insert", article.title)
                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}