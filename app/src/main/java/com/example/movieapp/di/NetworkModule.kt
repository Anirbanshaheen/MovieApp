package com.example.movieapp.di

import com.example.movieapp.api.MovieBannerAPI
import com.example.movieapp.api.RetrofitUtils.retrofitInstance
import com.example.movieapp.utils.Constants.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Named("apiOmdb")
    fun provideBaseUrlUnsplash() = BASE_URL


    @Provides
    @Singleton
    fun provideRetrofitInstance(@Named("apiOmdb") BASE_URL: String, gson: Gson, httpClient: OkHttpClient): MovieBannerAPI =
        retrofitInstance(baseUrl = BASE_URL, gson, httpClient)
            .create(MovieBannerAPI::class.java)

}