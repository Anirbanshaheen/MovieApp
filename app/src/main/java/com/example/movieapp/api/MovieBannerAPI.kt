package com.example.movieapp.api

import com.example.movieapp.models.ErrorResponse
import com.example.movieapp.models.MovieBannerModel
import com.example.movieapp.models.MovieDetails
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

// All api interfaces
interface MovieBannerAPI {
    @GET(".")
    suspend fun getMovieBanner(@Query("s") s: String, @Query("apikey") apikey: String) : NetworkResponse<MovieBannerModel, ErrorResponse>

    @GET(".")
    suspend fun getLatestMovie(@Query("s") s: String, @Query("y") y: Int, @Query("apikey") apikey: String) : NetworkResponse<MovieBannerModel, ErrorResponse>

    @GET(".")
    suspend fun getMovieDetails(@Query("t") t: String, @Query("y") y: Int, @Query("apikey") apikey: String) : NetworkResponse<MovieDetails, ErrorResponse>

    @GET(".")
    suspend fun getLatestMoviePagination(@Query("s") s: String, @Query("page") page: Int, @Query("y") y: Int, @Query("apikey") apikey: String) : NetworkResponse<MovieBannerModel, ErrorResponse>
}