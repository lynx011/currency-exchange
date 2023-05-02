package com.example.data_binding.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data_binding.R
import com.example.data_binding.databinding.CountryItemBinding
import com.example.data_binding.model.CurrencyCode
import java.util.*

class CountriesAdapter : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>(),Filterable {
    private lateinit var binding: CountryItemBinding

    var countriesList = emptyList<CurrencyCode>()
    var filteredList = emptyList<CurrencyCode>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateFiltered(items: List<CurrencyCode>){
        countriesList = items
        filteredList = items
        differ.submitList(items)
        notifyDataSetChanged()
    }

    var onItemClick : ((CurrencyCode) -> Unit)? = null
    inner class CountriesViewHolder(val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindCodes(codes: CurrencyCode){
            binding.codes = codes
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater,R.layout.country_item,parent,false)
        return CountriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val codes = differ.currentList[position]
        if (codes != null) {
            holder.bindCodes(codes)
        }
        holder.itemView.setOnClickListener {
            if (codes != null) {
                onItemClick?.invoke(codes)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<CurrencyCode>(){

        override fun areItemsTheSame(oldItem: CurrencyCode, newItem: CurrencyCode): Boolean {
            return oldItem.codes == newItem.codes
        }

        override fun areContentsTheSame(oldItem: CurrencyCode, newItem: CurrencyCode): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(text: CharSequence?): FilterResults {
                val query = text.toString().trim().lowercase(Locale.getDefault())
                filteredList = if (query.isNotEmpty()){
                    countriesList.filter { code ->
                       code.countries.lowercase(Locale.getDefault()).contains(query) ||
                               code.codes.lowercase(Locale.getDefault()).contains(query)
                    }
                }else{
                    countriesList
                }
                return FilterResults().apply {
                    values = filteredList
                }
            }

            override fun publishResults(text: CharSequence?, result: FilterResults?) {
                    countriesList = result?.values as List<CurrencyCode>
                    differ.submitList(countriesList)

            }

        }
    }

}