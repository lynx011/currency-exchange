package com.example.data_binding.fragments
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.data_binding.R
import com.example.data_binding.connection.NetworkConnection
import com.example.data_binding.databinding.FragmentConverterBinding
import com.example.data_binding.view_model.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ConverterFragment : Fragment() {
    private lateinit var binding: FragmentConverterBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var parsedAmount: Number? = null
    private val viewModel : CurrencyViewModel by viewModels()
    private val networkConnection by lazy { context?.let { NetworkConnection(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_converter, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        performInNetworkStates()
    }

    @SuppressLint("SetTextI18n", "CommitPrefEdits", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences =
            requireContext().getSharedPreferences("SharedValue", Context.MODE_PRIVATE)

        binding.firstCard.setOnClickListener {
            passKey("firstCode", "firstNation")
        }

        binding.secondCard.setOnClickListener {
            passKey("secondCode", "secondNation")
        }

        viewModel.firstCodeLiveData.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()){
                    binding.firstCode.text = it.toString()
                }
        }

        viewModel.secondCodeLiveData.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()){
                    binding.secondCode.text = it.toString()
                }
        }

        binding.firstValue.setOnClickListener {
            findNavController().navigate(R.id.action_converterFragment_to_quantityFragment)
        }

        binding.pickDate.setOnClickListener {
            viewModel.pickedDate(requireContext())
        }

        val amountValue = sharedPreferences.getString("amountValue", null)

        if (amountValue != null) {
            binding.firstValue.text = amountValue
        } else {
            binding.firstValue.text = "1"
        }

        binding.switchBtn.setOnClickListener {
            switchData()
        }

        passedDataFromCurrencies()
        setCodeAndNation()

    }

    @SuppressLint("SimpleDateFormat")
    private fun onlineConverter(){
        viewModel.selectedDateLiveData.observe(viewLifecycleOwner){ date ->
            val firstCode = binding.firstCode.text.toString()
            val secondCode = binding.secondCode.text.toString()

            val parsedValue = Integer.parseInt(binding.firstValue.text.toString())
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currentDate = sdf.format(Date())
            if (date.isNotEmpty()){
                binding.pickedDateTv.text = date.toString()
                viewModel.getConvertedCurrency(secondCode,firstCode,parsedValue,date)

            } else{
                binding.pickedDateTv.text = currentDate.toString()
                viewModel.getConvertedCurrency(secondCode,firstCode,parsedValue,currentDate)
            }
        }

        viewModel.convertAmountLiveData.observe(viewLifecycleOwner){
            viewModel.secondValue(it?.result.toString())
        }

        viewModel.secondValueLiveData.observe(viewLifecycleOwner){
            binding.secondValue.text = it.toString()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun offlineConverter() {
        val code = binding.secondCode.text.toString()
        lifecycleScope.launch {
            viewModel.matchQuery(code)
                .observe(viewLifecycleOwner) { currency ->
                    if (currency != null) {
                        binding.apply {
                            firstCode.text = "USD"
                            firstCountry.text = "United States Dollar"
                            firstValue.text = "1"
                            secondValue.text = currency.exchangeRate.toString()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "nothing found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun passedDataFromCurrencies() {
        val args = arguments
        val getCode = args?.getString("code")
        val getRate = args?.getString("rate")
        getCode?.let { viewModel.setCodes(it) }
        getRate?.let { viewModel.setValues(it) }
        viewModel.setCodeLiveData.observe(viewLifecycleOwner) {
            val firstSub = it.substring(0, 3)
            val secondSub = it.substring(3)
            binding.firstCode.text = firstSub
            binding.secondCode.text = secondSub
            if (firstSub.isNotEmpty()) {
                binding.firstCode.text = firstSub
                binding.text.text = firstSub
            }
            if (secondSub.isNotEmpty()) {
                binding.secondCode.text = secondSub
                binding.text1.text = secondSub
            }
        }
        viewModel.setValueLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
            if (it.isNotEmpty()){
                binding.firstValue.text = "1"
                binding.secondValue.text = it.toString()
            }
        }
    }

    private fun switchData() {
        viewModel.code = binding.firstCode.text.toString()
        viewModel.country = binding.firstCountry.text.toString()
        viewModel.result = binding.firstValue.text.toString()
        val code = viewModel.code
        val country = viewModel.country
        val result = viewModel.result
        binding.firstCode.text = binding.secondCode.text
        binding.firstCountry.text = binding.secondCountry.text
        binding.firstValue.text = binding.secondValue.text
        binding.secondCode.text = code
        binding.secondCountry.text = country
        binding.secondValue.text = result
    }

    private fun setCodeAndNation() {
        val code1 = sharedPreferences.getString("firstCode", null)
        val code2 = sharedPreferences.getString("secondCode", null)
        val nation1 = sharedPreferences.getString("firstNation", null)
        val nation2 = sharedPreferences.getString("secondNation", null)
//        val code1 = arguments?.getString("firstCode")
//        val code2 = arguments?.getString("secondCode")
//        val nation1 = arguments?.getString("firstNation")
//        val nation2 = arguments?.getString("secondNation")
        code1?.let { viewModel.setFirstCode(it) }
        code2?.let { viewModel.setSecondCode(it) }
        nation1?.let { viewModel.setNation1(it) }
        nation2?.let { viewModel.setNation2(it) }
        viewModel.firstCodeLiveData.observe(viewLifecycleOwner) {
            binding.firstCode.text = it
        }
        viewModel.secondCodeLiveData.observe(viewLifecycleOwner) {
            binding.secondCode.text = it
        }
        viewModel.nation1LiveData.observe(viewLifecycleOwner) {
            binding.firstCountry.text = it
        }
        viewModel.nation2LiveData.observe(viewLifecycleOwner) {
            binding.secondCountry.text = it
        }
    }

    private fun passKey(keyType: String, nation: String) {
        findNavController().navigate(
            R.id.searchCodeFragment,
            bundleOf("keyType" to keyType, "nation" to nation)
        )
    }

   private fun performInNetworkStates(){
       networkConnection?.observe(viewLifecycleOwner){ isConnected ->
           if (isConnected){
               binding.onlineAnim.isVisible = true
               binding.offlineAnim.isVisible = false
               onlineConverter()
           }else{
               Handler(Looper.getMainLooper()).postDelayed({
                   binding.offlineAnim.isVisible = true
                   binding.onlineAnim.isVisible = false
                   offlineConverter()
               },300)
           }
       }
   }

}