package com.jae1jeong.currencycalculate.data.remote.dto

data class ExchangeRateResponse(
    val privacy: String,
    val quotes: Quotes,
    val source: String,
    val success: Boolean,
    val terms: String,
    val timestamp: Long
)