package com.example.data_binding.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data_binding.R
import com.example.data_binding.databinding.CurrencyItemBinding
import com.example.data_binding.model.CurrencyModel
import com.example.data_binding.model.Quotes

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {
    private lateinit var binding: CurrencyItemBinding

    private var currenciesList = mutableListOf<Quotes>()
    @SuppressLint("NotifyDataSetChanged")
    fun setCurrencies(currency: List<Quotes>){
        currenciesList.clear()
        currenciesList = currency.toMutableList()
        notifyDataSetChanged()

    }

    inner class CurrencyViewHolder(val binding: CurrencyItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindData(currencyData: Quotes){
            binding.currency = currencyData
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.currency_item,parent,false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currencies = currenciesList[position]
        holder.bindData(currencies)
    }

    override fun getItemCount(): Int {
        return currenciesList.size
    }

}