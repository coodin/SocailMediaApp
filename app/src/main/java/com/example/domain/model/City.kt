package com.example.domain.model

import com.example.utility.Country

data class City(
    val country: String? = null,
    val isCapital: Boolean? = null,
    val name: String? = null,
    val population: Long? = null,
    val regions: List<String>? = null,
    val state: String? = null
)