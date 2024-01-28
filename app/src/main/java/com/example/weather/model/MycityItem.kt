package com.example.weather.model

data class MycityItem(
    val country: String,
    val lat: Float,
    val local_names: LocalNames,
    val lon: Float,
    val name: String,
    val state: String
)