package com.example.data_binding.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import retrofit2.http.Url

@BindingAdapter("loadImage")
fun loadImageUrl(view: ImageView,imgUrl: String){
    Glide.with(view)
        .load(imgUrl)
        .into(view)
}

@BindingAdapter("doubleExchange")
fun doubleExchange(view: TextView,text: Double){
    view.text = String.format("%.2f",text)
}