package com.example.data_binding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.data_binding.R
import com.example.data_binding.databinding.FragmentNewsDetailsBinding


class NewsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_news_details,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        binding.apply {
            title.text = args?.getString("title")
            desc.text = args?.getString("desc")
            Glide.with(this@NewsDetailsFragment)
                .load(args?.getString("thumb"))
                .into(imgView)
        }
    }

}