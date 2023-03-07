package com.example.data_binding.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data_binding.R
import com.example.data_binding.adapter.CurrencyAdapter
import com.example.data_binding.api_service.CurrencyApi
import com.example.data_binding.databinding.FragmentCurrenciesBinding
import com.example.data_binding.repository.CurrencyRepository
import com.example.data_binding.room.CurrencyDatabase
import com.example.data_binding.view_model.CurrencyViewModel
import com.example.data_binding.view_model_factory.CurrencyViewModelFactory
import kotlinx.coroutines.launch

class CurrenciesFragment : Fragment() {
    private lateinit var binding: FragmentCurrenciesBinding
    private lateinit var viewModel: CurrencyViewModel
    private lateinit var currenciesAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_currencies,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = CurrencyApi.getInstance()
        val db = CurrencyDatabase.getInstance(requireContext())
        val repository = CurrencyRepository(apiService,db)

        viewModel = ViewModelProvider(this,CurrencyViewModelFactory(repository))[CurrencyViewModel::class.java]

        binding.recView.apply {
            layoutManager = LinearLayoutManager(context)
            currenciesAdapter = CurrencyAdapter()
            adapter = currenciesAdapter
        }

        lifecycleScope.launch {
            viewModel.currenciesLiveData.observe(viewLifecycleOwner){ quotes ->
                    currenciesAdapter.setCurrencies(quotes)
                Log.d("quotes",quotes.toString())
            }
        }

        viewModel.getCurrencies()

    }

}