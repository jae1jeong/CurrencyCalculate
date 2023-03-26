package com.jae1jeong.currencycalculate.presentation.model

sealed class ReceiveCountry(val countryName: String, val countryCode: String){
    object Korea : ReceiveCountry("한국", "KRW")
    object Japan : ReceiveCountry("일본", "JPY")
    object Philippines : ReceiveCountry("필리핀", "PHP")
}
