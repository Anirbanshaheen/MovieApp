package com.example.movieapp.ListingPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.MovieBannerModel
import com.example.movieapp.models.MovieDetails
import com.example.movieapp.repository.MovieBannerRepository
import com.example.movieapp.utils.ViewState
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieBannerRepository: MovieBannerRepository) : ViewModel() {

    val viewState = MutableLiveData<ViewState>(ViewState.NONE)

    private val _data = MutableLiveData<MovieDetails>()
    val data: LiveData<MovieDetails> = _data

    fun getMovieDetails(t: String, y: Int, apikey: String) {

        viewState.value = ViewState.ProgressState(true)

        viewModelScope.launch(Dispatchers.IO) {
            val response = movieBannerRepository.getMovieDetails(t, y, apikey)
            withContext(Dispatchers.Main) {
                viewState.value = ViewState.ProgressState(false)
                when (response) {
                    is NetworkResponse.Success -> {
                        _data.value = response.body
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