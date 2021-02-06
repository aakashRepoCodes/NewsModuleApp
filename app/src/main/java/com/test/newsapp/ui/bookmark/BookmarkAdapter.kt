package com.test.newsapp.ui.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.newsapp.data.model.Article
import com.test.pokemongo.R


class BookmarkAdapter(private val items: List<Article>, context :Context ) :
    RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    private val context: Context

    init {
        this.context = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)

        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    class ViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

        lateinit var newsImage: ImageView
        lateinit var newsChannel: TextView
        lateinit var newsDesc: TextView
        lateinit var newsTitle: TextView

        init {
            newsImage= itemView.findViewById(R.id.news_image)
            newsChannel=  itemView.findViewById(R.id.news_channel)
            newsDesc=  itemView.findViewById(R.id.news_desc)
            newsTitle= itemView.findViewById(R.id.news_title)
            newsImage= itemView.findViewById(R.id.news_image)
        }

        fun bindData(item: Article) {
            Glide.with(context)
                .load(item.urlToImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(newsImage)
            //newsChannel.text = item.source.name
            newsDesc.text = item.description
            newsTitle.text = item.title
        }
    }
}
