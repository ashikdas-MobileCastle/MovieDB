package de.mobilecastle.moviedb.repository

import androidx.lifecycle.LiveData
import de.mobilecastle.moviedb.api.TheMovieDBInterface
import de.mobilecastle.moviedb.data.MovieDetails
import de.mobilecastle.moviedb.datasource.MovieDetailsDataSource
import de.mobilecastle.moviedb.utilities.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (val apiService: TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.movieDetalsResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}