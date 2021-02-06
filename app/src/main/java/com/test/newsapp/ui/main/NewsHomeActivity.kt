package com.test.newsapp.ui.main


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.newsapp.MyApplication
import com.test.newsapp.data.model.Article
import com.test.newsapp.database.DatabaseService
import com.test.newsapp.di.component.DaggerActivityComponent
import com.test.newsapp.di.module.ActivityModule
import com.test.newsapp.ui.adapter.Adapter
import com.test.newsapp.ui.adapter.Adapter.OnItemClickListener
import com.test.newsapp.ui.base.BaseActivity
import com.test.newsapp.ui.bookmark.BookmarkActivity
import com.test.newsapp.ui.detail.NewsDetailActivity
import com.test.newsapp.viewmodels.NewsViewModel
import com.test.pokemongo.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


class NewsHomeActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: NewsViewModel

    private lateinit var adapter: Adapter
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var articles: List<Article> = ArrayList()

    @Inject
    lateinit var databaseService: DatabaseService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDependencies()
        layoutManager = LinearLayoutManager(this@NewsHomeActivity)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = false
        setSupportActionBar(toolbarHome)
        viewModel.readNews()
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

    override fun provideLayout() = R.layout.activity_main

    private fun observeLiveData() {

        viewModel.newsFailedLiveData.observe(this, androidx.lifecycle.Observer {
            Toast.makeText(this, "failed", Toast.LENGTH_LONG).show()
        })

        viewModel.newsLiveData.observe(this, androidx.lifecycle.Observer {
            articles = it!!
            adapter = Adapter(it!!, this@NewsHomeActivity)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

            adapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val intent = Intent(this@NewsHomeActivity, NewsDetailActivity::class.java)
                    intent.putExtra("url", articles[position].url)
                    intent.putExtra("img", articles[position].urlToImage)
                    intent.putExtra("title", articles[position].title)
                    intent.putExtra("date", articles[position].publishedAt)
                    intent.putExtra("source", articles[position].title)
                    intent.putExtra("author", articles[position].author)
                    startActivity(intent)
                }
            })
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                //startActivity(Intent(this, BookmarkActivity::class.java))
            }
            R.id.action_favorite -> {
                startActivity(Intent(this, BookmarkActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
