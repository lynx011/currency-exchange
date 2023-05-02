package com.example.data_binding.view_model
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data_binding.R
import com.example.data_binding.model.*
import com.example.data_binding.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: CurrencyRepository) : ViewModel() {

    val currenciesCodes = MutableLiveData<List<CurrencyCode>>()
    val convertAmountLiveData = MutableLiveData<ConvertModel?>()
    val selectedDateLiveData = MutableLiveData("")
    val setCodeLiveData = MutableLiveData<String>()
    val setValueLiveData = MutableLiveData<String>()
    var selectedDate = MutableLiveData<String>()
    var firstCodeLiveData = MutableLiveData("")
    var secondCodeLiveData = MutableLiveData("")
    var secondValueLiveData = MutableLiveData("")
    var nation1LiveData = MutableLiveData("")
    var nation2LiveData = MutableLiveData("")
    var code = ""
    var country = ""
    var result = ""

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, exception ->
        exception.localizedMessage?.let { Log.d("exception", it) }
    }

    init {
        getCurrenciesCodes()
    }

    fun getCurrenciesRates(date: String,source: String) = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
        val response = repository.getCurrencies(date,source).body()
            if (response != null) {
               val data = response.quotes.toList()
                val quote = data.map { ratePair ->
                    CurrencyDetail(0, ratePair.first, ratePair.second,response.date)
                }
                withContext(Dispatchers.Main){
                    repository.insertCurrencies(quote)
                }
            }
    }

    fun getCurrenciesCodes() = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
        val response = repository.getCountries().body()
            if (response != null) {
                val data = response.currencies.toList()
                val codes = data.map { codePair ->
                    CurrencyCode(0, codePair.first, codePair.second)
                }
                    withContext(Dispatchers.Main){
                        repository.insertCodes(codes)
                    }
            }
    }

    fun getConvertedCurrency(to: String, from: String, amount: Int, date: String) = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
        val response = repository.getConvertAmount(to,from,amount,date)
        withContext(Dispatchers.Main){
            convertAmountLiveData.value = response
        }
    }

    val getRoomCodes : LiveData<List<CurrencyCode>> = repository.getRoomCodes()

    suspend fun matchQuery(query: String) = repository.searchQuery(query)

    fun deleteCurrency() {
        repository.deleteCurrency()
    }

    fun pickedDate(context: Context) {
        val calendar = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val month = monthOfYear + 1
                var formattedMonth = "" + month
                var formattedDay = "" + dayOfMonth
                if (month < 10) {
                    formattedMonth = "0$month"
                }
                if (dayOfMonth < 10) {
                    formattedDay = "0$dayOfMonth"
                }
                selectedDateLiveData.value = "$year-$formattedMonth-$formattedDay"
            }

        val dialog = DatePickerDialog(
            context, R.style.DialogTheme, dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.maxDate = calendar.timeInMillis
        dialog.show()
    }

    fun setCodes(codes: String) {
        setCodeLiveData.value = codes
    }

    fun setValues(values: String) {
        setValueLiveData.value = values
    }

    fun setFirstCode(code : String){
        firstCodeLiveData.value = code
    }

    fun setSecondCode(code: String){
        secondCodeLiveData.value = code
    }

    fun setNation1(name: String){
        nation1LiveData.value = name
    }

    fun setNation2(name: String){
        nation2LiveData.value = name
    }

    fun setCurrentDate(date: String) {
        selectedDate.value = date
    }

    fun secondValue(value: String){
        secondValueLiveData.value = value
    }
}