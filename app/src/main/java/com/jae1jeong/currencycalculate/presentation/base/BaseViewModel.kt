package com.jae1jeong.currencycalculate.presentation.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(){



    protected val _unexpectedErrorEvent = SingleLiveEvent<Void>()
    val unexpectedErrorEvent get() = _unexpectedErrorEvent

}