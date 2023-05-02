package com.example.data_binding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data_binding.databinding.LoadMoreBinding

class LoadMoreAdapter(private val reload: () -> Unit) : LoadStateAdapter<LoadMoreAdapter.LoadMoreViewHolder>() {

    private lateinit var binding: LoadMoreBinding

    class LoadMoreViewHolder(val binding: LoadMoreBinding,reload: () -> Unit) : RecyclerView.ViewHolder(binding.root){
        fun setLoadState(loadState: LoadState){
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
//                text.isVisible = loadState is LoadState.Error
//                reloadBtn.isVisible = loadState is LoadState.Error
            }
        }
    }

    override fun onBindViewHolder(holder: LoadMoreViewHolder, loadState: LoadState) {
        holder.setLoadState(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadMoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = LoadMoreBinding.inflate(inflater,parent,false)
        return LoadMoreViewHolder(binding,reload)
    }
}