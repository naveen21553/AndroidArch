package com.mba.sample.di

import com.mba.sample.model.CountryService
import com.mba.sample.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: CountryService)

    fun inject(viewModel: ListViewModel)
}