package com.mba.sample.di

import com.mba.sample.model.CountryAPI
import com.mba.sample.model.CountryService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://raw.githubusercontent.com"
    @Provides
    fun provideCountryApi(): CountryAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountryAPI::class.java)
    }

    @Provides
    fun provideCountryService(): CountryService {
        return CountryService()
    }

}