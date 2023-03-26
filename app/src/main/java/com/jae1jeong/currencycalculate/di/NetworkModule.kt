package com.jae1jeong.currencycalculate.di

import com.jae1jeong.currencycalculate.BuildConfig
import com.jae1jeong.currencycalculate.data.remote.api.ApiLayerApi
import com.jae1jeong.currencycalculate.domain.repository.ApiLayerRepositoryImpl
import com.jae1jeong.currencycalculate.domain.usecase.GetExchangeRateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideApiLayerService(retrofit: Retrofit): ApiLayerApi {
        return retrofit.create(ApiLayerApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApiLayerRepository(apiLayerApi: ApiLayerApi) = ApiLayerRepositoryImpl(apiLayerApi)

    @Singleton
    @Provides
    fun provideGetExchangeRateUseCase(apiLayerRepository: ApiLayerRepositoryImpl) =
        GetExchangeRateUseCase(apiLayerRepository)
}