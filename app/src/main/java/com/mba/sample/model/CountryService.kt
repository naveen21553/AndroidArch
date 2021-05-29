package com.mba.sample.model

import com.mba.sample.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class CountryService {

    @Inject
    lateinit var api: CountryAPI

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCountries() : Single<List<Country>> = api.getCountries()
}