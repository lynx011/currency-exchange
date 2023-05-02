package com.example.data_binding.fragments
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data_binding.R
import com.example.data_binding.adapter.CountriesAdapter
import com.example.data_binding.connection.NetworkConnection
import com.example.data_binding.databinding.FragmentSearchCodeBinding
import com.example.data_binding.model.CurrencyCode
import com.example.data_binding.utils.CodeSelectedListener
import com.example.data_binding.view_model.CurrencyViewModel
import com.example.data_binding.view_model.PagingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCodeFragment : Fragment() {
    private lateinit var binding: FragmentSearchCodeBinding
    private lateinit var codeAdapter: CountriesAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel : CurrencyViewModel by viewModels()
    private val pagingViewModel : PagingViewModel by viewModels()
    private val networkConnection by lazy { context?.let { NetworkConnection(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString("type").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_code, container, false)
        return binding.root
    }

    @SuppressLint("CommitPrefEdits", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val keyType = args?.getString("keyType")
        val nation = args?.getString("nation")

        sharedPreferences =
            requireContext().getSharedPreferences("SharedValue", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.lottie.isVisible = true

        var countriesList = listOf<CurrencyCode>()

        binding.recView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            codeAdapter = CountriesAdapter()
            adapter = codeAdapter
        }

            viewModel.getRoomCodes.observe(viewLifecycleOwner){ codes ->
                codeAdapter.differ.submitList(codes)
                binding.lottie.isVisible = false
                countriesList = codes
            }

        binding.searchView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                codeAdapter.filter.filter(query)
                codeAdapter.updateFiltered(countriesList)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        val bundle = Bundle()
        codeAdapter.onItemClick = { data ->
            Toast.makeText(requireContext(), data.countries, Toast.LENGTH_SHORT).show()
//            listener?.getSelectedCode(type, data.codes)
            viewModel.firstCodeLiveData.value = data.codes
            editor.putString(keyType,data.codes).apply()
            editor.putString(nation,data.countries).apply()
//            bundle.putString(keyType,data.codes)
//            bundle.putString(nation,data.countries)
            val fragment = ConverterFragment()
            fragment.arguments = bundle
            findNavController().popBackStack()
        }

        binding.backKey.setOnClickListener {
            findNavController().popBackStack()
        }

//        lifecycleScope.launch {
//            codeAdapter.loadStateFlow.collectLatest { isRefresh ->
//                val state = isRefresh.refresh
//                binding.lottie.isVisible = state is LoadState.Loading
//            }
//        }

        viewModel.getCurrenciesCodes()

    }

    companion object {
        private var listener: CodeSelectedListener? = null
        private var type: String = ""

        @JvmStatic
        fun newInstance(keyType: String, callback: CodeSelectedListener) =
            SearchCodeFragment().apply {
                listener = callback
                arguments = Bundle().apply {
                    putString("type", keyType)
                }
            }
    }

}