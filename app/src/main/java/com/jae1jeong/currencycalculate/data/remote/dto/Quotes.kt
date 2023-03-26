package com.jae1jeong.currencycalculate.data.remote.dto

import android.util.Log

data class Quotes constructor(
    val USDPHP: Double,
    val USDKRW: Double,
    val USDJPY: Double,
){
    val countryRateMap :HashMap<String,Double> get() {
        return hashMapOf(
            "USDPHP" to USDPHP,
            "USDKRW" to USDKRW,
            "USDJPY" to USDJPY
        )
    }


}