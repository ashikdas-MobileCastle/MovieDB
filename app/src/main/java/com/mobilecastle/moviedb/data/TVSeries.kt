package com.mobilecastle.moviedb.data

import com.google.gson.annotations.SerializedName

data class TVSeries (
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    val name: String
)