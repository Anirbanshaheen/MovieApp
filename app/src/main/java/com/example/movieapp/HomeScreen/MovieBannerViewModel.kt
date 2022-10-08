package com.example.movieapp.HomeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.MovieBannerModel
import com.example.movieapp.repository.MovieBannerRepository
import com.haroldadmin.cnradapter.NetworkResponse
import com.example.movieapp.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieBannerViewModel @Inject constructor(private val movieBannerRepository: MovieBannerRepository) : ViewModel() {

    val viewState = MutableLiveData<ViewState>(ViewState.NONE)

    // dataList for observe specific data
    private val _dataList = MutableLiveData<MovieBannerModel>()
    val dataList: LiveData<MovieBannerModel> = _dataList

    // All API needed form Repository
    fun getMovieBanners(s: String, apikey: String): LiveData<MovieBannerModel> {

        viewState.value = ViewState.ProgressState(true)
        val responseBody = MutableLiveData<MovieBannerModel>()

        viewModelScope.launch(Dispatchers.IO) {
            val response = movieBannerRepository.getMovieBanners(s, apikey)
            withContext(Dispatchers.Main) {
                viewState.value = ViewState.ProgressState(false)
                when (response) {
                    is NetworkResponse.Success -> {
                        responseBody.value = response.body
                    }
                    is NetworkResponse.ServerError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.NetworkError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.UnknownError -> {
                        val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                }
            }
        }
        return responseBody
    }

    fun getLatestMovie(s: String, y: Int, apikey: String): LiveData<MovieBannerModel> {

        viewState.value = ViewState.ProgressState(true)
        val responseBody = MutableLiveData<MovieBannerModel>()

        viewModelScope.launch(Dispatchers.IO) {
            val response = movieBannerRepository.getLatestMovie(s, y, apikey)
            withContext(Dispatchers.Main) {
                viewState.value = ViewState.ProgressState(false)
                when (response) {
                    is NetworkResponse.Success -> {
                        responseBody.value = response.body
                    }
                    is NetworkResponse.ServerError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.NetworkError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.UnknownError -> {
                        val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                }
            }
        }
        return responseBody
    }

    fun getLatestMoviePagination(s: String, page: Int, y: Int, apikey: String) {

        viewState.value = ViewState.ProgressState(true)

        viewModelScope.launch(Dispatchers.IO) {
            val response = movieBannerRepository.getLatestMoviePagination(s, page, y, apikey)
            withContext(Dispatchers.Main) {
                viewState.value = ViewState.ProgressState(false)
                when (response) {
                    is NetworkResponse.Success -> {
                        _dataList.value = response.body
                    }
                    is NetworkResponse.ServerError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.NetworkError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.UnknownError -> {
                        val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                }
            }
        }
    }
}