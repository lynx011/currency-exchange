package com.example.data_binding.utils

import com.example.data_binding.model.Article

interface ItemClickListener {
    fun onClicked(article: Article)
}