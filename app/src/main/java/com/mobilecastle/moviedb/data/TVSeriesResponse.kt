package com.mobilecastle.moviedb.data

import com.google.gson.annotations.SerializedName

data class TVSeriesResponse(
    val page: Int,
    @SerializedName("results")
    val tvSeriesList: List<TVSeries>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
