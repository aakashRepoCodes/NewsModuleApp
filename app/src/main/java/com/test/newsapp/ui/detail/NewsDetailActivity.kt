package com.test.newsapp.ui.detail

import android.os.Bundle

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.test.newsapp.utils.Utils
import com.test.newsapp.utils.Utils.DateToTimeFormat
import com.test.pokemongo.R
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    private var isHideToolbarView = false

    private var mUrl: String? = null
    private var mImg: String? = null
    private var mTitle: String? = null
    private var mDate: String? = null
    private var mSource: String? = null
    private var mAuthor: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val collapsingToolbarLayout =
            findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        collapsingToolbarLayout.title = ""

        val intent = intent
        mUrl = intent.getStringExtra("url")
        mImg = intent.getStringExtra("img")
        mTitle = intent.getStringExtra("title")
        mDate = intent.getStringExtra("date")
        mSource = intent.getStringExtra("source")
        mAuthor = intent.getStringExtra("author")

        val requestOptions = RequestOptions()
        requestOptions.error(Utils.randomDrawbleColor)
        Glide.with(this)
            .load(mImg)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(backdrop)

        title_on_appbar.text = mSource
        subtitle_on_appbar.text = mUrl
        date.text = Utils.DateFormat(mDate);

        appbarTitle.text = mTitle
        val author: String
        author = if (mAuthor != null) {
            " \u2022 $mAuthor"
        } else {
            ""
        }
        time.text = "$mSource$author \u2022 " + DateToTimeFormat(mDate)
        initWebView(mUrl)
    }

    private fun initWebView(url: String?) {
        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOffsetChanged(
        appBarLayout: AppBarLayout,
        verticalOffset: Int
    ) {
        val maxScroll = appBarLayout.totalScrollRange
        val percentage =
            Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()
        if (percentage == 1f && isHideToolbarView) {
            date_behavior!!.visibility = View.GONE
            title_appbar.visibility = View.VISIBLE
            isHideToolbarView = !isHideToolbarView
        } else if (percentage < 1f && !isHideToolbarView) {
            date_behavior!!.visibility = View.VISIBLE
            title_appbar.visibility = View.GONE
            isHideToolbarView = !isHideToolbarView
        }
    }
}