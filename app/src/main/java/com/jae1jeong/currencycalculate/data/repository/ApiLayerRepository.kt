package com.jae1jeong.currencycalculate.data.repository

import com.jae1jeong.currencycalculate.data.remote.dto.ExchangeRateResponse

interface ApiLayerRepository {
    suspend fun getExchangeRate(): ExchangeRateResponse
}