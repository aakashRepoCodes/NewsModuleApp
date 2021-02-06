package com.test.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.newsapp.data.model.Article
import com.test.newsapp.ui.adapter.NewsAdapter.MyViewHolder
import com.test.newsapp.ui.main.NewsHomeActivity
import com.test.newsapp.utils.Utils
import com.test.pokemongo.R

class NewsAdapter(
    articles: List<Article>,
    context: Context
) : RecyclerView.Adapter<MyViewHolder>() {
    private val articles: List<Article>
    private val context: Context
    private var onItemClickListener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holders: MyViewHolder, position: Int) {
        val model: Article = articles[position]

        Glide.with(context)
            .load(model.urlToImage)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holders.imageView)

        holders.title.text = model.title
        holders.desc.text = model.description
       // holders.source.text = model.source.name
        holders.published_ad.text = Utils.DateFormat(model.publishedAt)
        holders.author.text = model.author
        holders.bookMarkImageView.setOnClickListener {
            (context as NewsHomeActivity).viewModel.bookMarkArticle(model, true)
            Toast.makeText(context , "Article added to Bookmarks !", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    inner class MyViewHolder(
        itemView: View,
        onItemClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var title: TextView
        var desc: TextView
        var author: TextView
        var published_ad: TextView
        var source: TextView
        var imageView: ImageView
        var bookMarkImageView: ImageView
        var onItemClickListener: OnItemClickListener?

        override fun onClick(v: View) {
            onItemClickListener!!.onItemClick(v, adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
            title = itemView.findViewById(R.id.title)
            desc = itemView.findViewById(R.id.desc)
            author = itemView.findViewById(R.id.author)
            published_ad = itemView.findViewById(R.id.publishedAt)
            source = itemView.findViewById(R.id.source)
            imageView = itemView.findViewById(R.id.img)
            bookMarkImageView = itemView.findViewById(R.id.news_bookmark_home)
            this.onItemClickListener = onItemClickListener
        }
    }

    init {
        this.articles = articles
        this.context = context
    }
}