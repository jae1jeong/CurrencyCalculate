package com.jae1jeong.currencycalculate.domain.repository

import com.jae1jeong.currencycalculate.data.remote.api.ApiLayerApi
import com.jae1jeong.currencycalculate.data.remote.dto.ExchangeRateResponse
import com.jae1jeong.currencycalculate.data.repository.ApiLayerRepository
import javax.inject.Inject

class ApiLayerRepositoryImpl @Inject constructor(
    private val apiLayerApi: ApiLayerApi
) : ApiLayerRepository {
    override suspend fun getExchangeRate(): ExchangeRateResponse {
        return apiLayerApi.getExchangeRate()
    }
}