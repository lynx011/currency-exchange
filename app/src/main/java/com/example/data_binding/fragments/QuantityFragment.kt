package com.example.data_binding.fragments
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data_binding.R
import com.example.data_binding.adapter.AmountAdapter
import com.example.data_binding.api_service.CurrencyApi
import com.example.data_binding.databinding.FragmentQuantityBinding
import com.example.data_binding.model.NumberModel
import com.example.data_binding.repository.CurrencyRepository
import com.example.data_binding.room.CurrencyDatabase
import com.example.data_binding.view_model.CurrencyViewModel
import com.example.data_binding.view_model_factory.CurrencyViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class QuantityFragment : Fragment() {
    private lateinit var binding: FragmentQuantityBinding
    private lateinit var numAdapter: AmountAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel : CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quantity,container,false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("SharedValue",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        recyclerView = binding.recView

        val numList = arrayListOf("1","2","3","4","5","6","7","8","9",".","0","✓")
        recyclerView.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            val lists = numList.map {
                NumberModel(it)
            }
            numAdapter = AmountAdapter(lists as ArrayList<NumberModel>)
            adapter = numAdapter
        }

        numAdapter.onNumClicked = { num ->
            binding.amountText.append(num.num)
            increaseCount(binding.amountText)
        }

        binding.reduceNum.setOnClickListener {
            val amount = binding.amountText.text
            if (amount.toString().isNotEmpty()){
                val amountText = amount.substring(0,amount.length - 1)
                binding.amountText.text = amountText
            }
        }
        val bundle = Bundle()
        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener{

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_UP){
                    val lastItem = rv.findChildViewUnder(e.x,e.y)
                    if(lastItem != null && rv.getChildAdapterPosition(lastItem) == numAdapter.itemCount - 1){
                        val input = binding.amountText.text
                        if (input.isNotEmpty()){
                            editor.putString("amountValue",input.toString()).apply()
                            findNavController().popBackStack()

                        }else{
                            MotionToast.darkColorToast(requireActivity(), title = "Warning⚠️",
                                message = "please type the amount!",
                                style = MotionToastStyle.WARNING,
                                position = MotionToast.GRAVITY_BOTTOM,
                                duration = MotionToast.SHORT_DURATION,
                                font = ResourcesCompat.getFont(requireContext(),www.sanju.motiontoast.R.font.helveticabold)
                            )
                        }
                        return true
                    }
                }
                return false
            }
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })

        binding.dropdownBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun increaseCount(amountText: TextView) {
        val maxLength = 16
        if (amountText.text.length > maxLength) {
            val textSize = amountText.textSize - 2
            amountText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }
    }


}