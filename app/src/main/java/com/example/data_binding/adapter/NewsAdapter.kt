package com.example.data_binding.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data_binding.R
import com.example.data_binding.databinding.NewsItemBinding
import com.example.data_binding.model.Article
import com.example.data_binding.utils.ItemClickListener

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private lateinit var binding: NewsItemBinding

    private var articleList = ArrayList<Article>()
    private var listener: ItemClickListener? = null

    fun onClickListener(listener: ItemClickListener){
        this.listener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArticle(article: List<Article>){
        articleList.clear()
        this.articleList = article as ArrayList<Article>
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindData(article : Article){
            binding.article = article
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater,R.layout.news_item,parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val articles = articleList[position]
        holder.bindData(articles)
        holder.binding.itemClicked = listener
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

}