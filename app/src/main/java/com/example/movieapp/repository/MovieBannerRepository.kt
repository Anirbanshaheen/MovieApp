package com.example.movieapp.repository

import com.example.movieapp.api.MovieBannerAPI
import javax.inject.Inject

// Inject API Interface
class MovieBannerRepository @Inject constructor(private val movieBannerAPI: MovieBannerAPI) {

    suspend fun getMovieBanners(s: String, apikey: String) = movieBannerAPI.getMovieBanner(s, apikey)

    suspend fun getLatestMovie(s: String, y: Int, apikey: String) = movieBannerAPI.getLatestMovie(s, y, apikey)

    suspend fun getMovieDetails(t: String, y: Int, apikey: String) = movieBannerAPI.getMovieDetails(t, y, apikey)

    suspend fun getLatestMoviePagination(s: String, page: Int, y: Int, apikey: String) = movieBannerAPI.getLatestMoviePagination(s, page, y, apikey)

}