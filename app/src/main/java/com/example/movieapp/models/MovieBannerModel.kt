package com.example.movieapp.models

data class MovieBannerModel(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)