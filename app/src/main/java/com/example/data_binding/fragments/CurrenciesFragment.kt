package com.example.data_binding.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data_binding.R
import com.example.data_binding.adapter.CurrencyAdapter
import com.example.data_binding.connection.NetworkConnection
import com.example.data_binding.databinding.FragmentCurrenciesBinding
import com.example.data_binding.view_model.CurrencyViewModel
import com.example.data_binding.view_model.PagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CurrenciesFragment : Fragment() {
    private lateinit var binding: FragmentCurrenciesBinding
    private lateinit var currenciesAdapter: CurrencyAdapter
    private val networkConnection by lazy { context?.let { NetworkConnection(it) } }

    private val viewModel: CurrencyViewModel by viewModels()
    private val pagingViewModel: PagingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currencies, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat", "NotifyDataSetChanged", "CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recView.apply {
            layoutManager = LinearLayoutManager(context)
            currenciesAdapter = CurrencyAdapter()
            adapter = currenciesAdapter
        }

        currenciesAdapter.onItemClick = { currency ->
            findNavController().popBackStack()
            findNavController().navigate(
                R.id.converterFragment,
                bundleOf("code" to currency.countryCode, "rate" to currency.exchangeRate)
            )
        }

        binding.pickDate.setOnClickListener {
            viewModel.pickedDate(requireContext())
        }

        viewModel.selectedDateLiveData.observe(viewLifecycleOwner) {
            viewModel.selectedDateLiveData.observe(viewLifecycleOwner) { date ->
                if (date.isNotEmpty()) {
                    binding.pickedDateTv.text = date.toString()
                } else {
                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                    val currentDate = sdf.format(Date())
                    binding.pickedDateTv.text = currentDate.toString()
                }
            }
        }

        binding.pickedDateTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

                lifecycleScope.launch {
                    viewModel.getCurrenciesRates(binding.pickedDateTv.text.toString(), "USD")
                }

                pagingViewModel.currenciesData(binding.pickedDateTv.text.toString())
                    .observe(viewLifecycleOwner) { pagingData ->
                        lifecycleScope.launch {
                            currenciesAdapter.submitData(pagingData)
                        }
                    }
            }

        })

        lifecycleScope.launchWhenCreated {
            currenciesAdapter.loadStateFlow.collectLatest { isRefresh ->
                val state = isRefresh.refresh
                binding.lottie.isVisible = state is LoadState.Loading
            }
        }
//        binding.recView.adapter = currenciesAdapter.withLoadStateFooter(
//            LoadMoreAdapter {
//                currenciesAdapter.retry()
//            }
//        )

        binding.codeDropdown.setOnClickListener {
            findNavController().navigate(R.id.searchCodeFragment)
        }

    }

}