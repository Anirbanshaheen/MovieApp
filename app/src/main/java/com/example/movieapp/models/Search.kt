package com.example.movieapp.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// For passing data model through bundle
@Parcelize
data class Search(
    @SerializedName("imdbID")
    var imdbID: String? = "",
    @SerializedName("Poster")
    var poster: String? = "",
    @SerializedName("Title")
    var title: String? = "",
    @SerializedName("Type")
    var type: String? = "",
    @SerializedName("Year")
    var year: String? = ""
): Parcelable