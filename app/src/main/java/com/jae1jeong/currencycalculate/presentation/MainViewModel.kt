package com.jae1jeong.currencycalculate.presentation

import androidx.lifecycle.*
import com.jae1jeong.currencycalculate.domain.usecase.GetExchangeRateUseCase
import com.jae1jeong.currencycalculate.presentation.base.BaseViewModel
import com.jae1jeong.currencycalculate.presentation.model.ReceiveCountry
import com.jae1jeong.currencycalculate.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val getExchangeRateUseCase: GetExchangeRateUseCase
): BaseViewModel(){

    private val _currentReceiveCountry = MutableLiveData<ReceiveCountry>(ReceiveCountry.Korea)
    val currentReceiveCountry :LiveData<ReceiveCountry> get() = _currentReceiveCountry

    private val _currentSendAmount = MutableLiveData<Double>(0.00)
    val currentSendAmount :LiveData<Double> get() = _currentSendAmount

    private val _currentTimeStamp = MutableLiveData<Long>(0)
    val currentTimeStamp :LiveData<Long> get() = _currentTimeStamp

    private val _currentRate = MutableLiveData<Double>(0.00)
    val currentRate :LiveData<Double> get() = _currentRate

    private val _currentSendCountry = MutableLiveData<String>(USD)
    val currentSendCountry :LiveData<String> get() = _currentSendCountry

    val currentFromTo = combine(_currentSendCountry.asFlow(),currentReceiveCountry.asFlow().map { it.countryCode }) { sendCountry, receiveCountry ->
        "$receiveCountry/$sendCountry"
    }.onStart { emit("KRW/USD") }.asLiveData()


    private val _invalidInRangeValueEvent = MutableLiveData<Boolean>(false)
    val invalidInRangeValueEvent :LiveData<Boolean> get() = _invalidInRangeValueEvent



    private val ceh = CoroutineExceptionHandler { _, throwable ->
       _unexpectedErrorEvent.call()
    }

    // dialog display용
    val receiveCountryList = listOf(
        ReceiveCountry.Korea,
        ReceiveCountry.Japan,
        ReceiveCountry.Philippines
    )

    fun selectReceiveCountry(country:ReceiveCountry){
        _currentReceiveCountry.value = country
    }


    fun getExchangeRate(receiveCountry: ReceiveCountry) {
        if(!Validator.checkValidInRange(currentSendAmount.value ?: 0.00)){
            _invalidInRangeValueEvent.postValue(true)
            return
        }

        _invalidInRangeValueEvent.postValue(false)
        viewModelScope.launch(ceh + Dispatchers.IO) {
            val result = getExchangeRateUseCase()
            _currentTimeStamp.postValue(result.timestamp)
            currentFromTo.value?.let {
                val reversedCountry = it.split("/").reversed().joinToString("")
                _currentRate.postValue(result.quotes.countryRateMap[reversedCountry])
            }

        }
    }

    fun setCurrentSendAmount(amountStr: String?) {
        _currentSendAmount.value = Validator.onlyNumberExceptDot(amountStr ?: "")
    }

    companion object{
        const val USD = "USD"
    }
}