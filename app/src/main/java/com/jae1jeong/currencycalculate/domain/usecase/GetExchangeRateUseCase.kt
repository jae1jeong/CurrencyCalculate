package com.jae1jeong.currencycalculate.domain.usecase

import com.jae1jeong.currencycalculate.data.repository.ApiLayerRepository
import javax.inject.Inject

class GetExchangeRateUseCase @Inject constructor(private val apiLayerRepository: ApiLayerRepository) {
    suspend operator fun invoke() = apiLayerRepository.getExchangeRate()
}