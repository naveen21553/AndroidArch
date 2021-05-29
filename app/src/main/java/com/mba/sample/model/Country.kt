package com.mba.sample.model

import com.google.gson.annotations.SerializedName


data class Country(
    @SerializedName("name")
    val countryName: String?,

    @SerializedName("capital")
    val capitalName: String?,

    val flagPNG: String?

)