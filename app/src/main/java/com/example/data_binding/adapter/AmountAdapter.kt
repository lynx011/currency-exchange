package com.example.data_binding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data_binding.R
import com.example.data_binding.databinding.NumItemBinding
import com.example.data_binding.model.NumberModel

class AmountAdapter(private val numList: ArrayList<NumberModel>) : RecyclerView.Adapter<AmountAdapter.AmountViewHolder>() {
    private lateinit var binding: NumItemBinding

    var onNumClicked : ((NumberModel) -> Unit)? = null

    inner class AmountViewHolder(val binding: NumItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.num_item,parent,false)
        return AmountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AmountViewHolder, position: Int) {
        val numList = numList[position]
        binding.num = numList
        holder.itemView.setOnClickListener {
            onNumClicked?.invoke(numList)
        }

    }

    override fun getItemCount(): Int {
        return numList.size
    }
}