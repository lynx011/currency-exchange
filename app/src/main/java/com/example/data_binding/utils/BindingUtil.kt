package com.example.data_binding.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import retrofit2.http.Url

@BindingAdapter("loadImage")
fun loadImageUrl(view: ImageView,imgUrl: String){
    Glide.with(view)
        .load(imgUrl)
        .into(view)
}