package com.example.movieapp.models


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("Error")
    var error: String? = "",
    @SerializedName("Response")
    var response: String? = ""
)