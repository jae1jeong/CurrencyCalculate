package com.jae1jeong.currencycalculate.data.remote.api

import com.jae1jeong.currencycalculate.BuildConfig
import com.jae1jeong.currencycalculate.data.remote.dto.ExchangeRateResponse
import com.jae1jeong.currencycalculate.utils.ApiRouter
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API Layer API
 */
interface ApiLayerApi {
    companion object{
        const val QUERY_ACCESS_KEY = "access_key"
    }

    /**
     * 환율 정보 조회
     */
    @GET(ApiRouter.LIVE)
    suspend fun getExchangeRate(@Query(QUERY_ACCESS_KEY) accessKey:String = BuildConfig.API_KEY): ExchangeRateResponse
}