package com.example.data_binding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data_binding.R
import com.example.data_binding.databinding.CurrencyItemBinding
import com.example.data_binding.model.CurrencyDetail

class CurrencyAdapter : PagingDataAdapter<CurrencyDetail,CurrencyAdapter.CurrencyViewHolder>(diffCallback = DiffCallback) {
    private lateinit var binding: CurrencyItemBinding

    var onItemClick: ((CurrencyDetail) -> Unit)? = null

    inner class CurrencyViewHolder(val binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(currencies: CurrencyDetail){
                binding.currency = currencies
                binding.executePendingBindings()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.currency_item, parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currencies = getItem(position)
        if (currencies != null) {
            holder.bind(currencies)
        }
        holder.itemView.setOnClickListener {
            if (currencies != null) {
                onItemClick?.invoke(currencies)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<CurrencyDetail>(){

        override fun areItemsTheSame(oldItem: CurrencyDetail, newItem: CurrencyDetail): Boolean {
            return oldItem.currencyId == newItem.currencyId
        }

        override fun areContentsTheSame(oldItem: CurrencyDetail, newItem: CurrencyDetail): Boolean {
            return oldItem == newItem
        }

    }

}