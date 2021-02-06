package com.test.newsapp.ui.search

import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.newsapp.MyApplication
import com.test.newsapp.di.component.DaggerActivityComponent
import com.test.newsapp.di.module.ActivityModule
import com.test.newsapp.ui.base.BaseActivity
import com.test.newsapp.ui.bookmark.BookmarkAdapter
import com.test.newsapp.viewmodels.BookMarkArticleViewModel
import com.test.newsapp.viewmodels.NewsViewModel
import com.test.pokemongo.R
import kotlinx.android.synthetic.main.activity_bookmark.*
import javax.inject.Inject


class SearchActivity : BaseActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null

    private lateinit var adapter: BookmarkAdapter

    @Inject
    lateinit var viewModel: NewsViewModel


    override fun provideLayout(): Int = R.layout.activity_bookmark

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.bookmark_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDependencies()
        viewModel.readNewsFromDB()
        layoutManager = LinearLayoutManager(this)
        content.layoutManager = layoutManager
        supportActionBar?.title = getString(R.string.search)
        observeLiveData()
    }

    private fun getDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MyApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }

    private fun observeLiveData() {
        viewModel.newsLocalLiveData.observe(this , Observer {
            adapter = BookmarkAdapter(it, this)
            content.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }

}
