package de.mobilecastle.moviedb.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.mobilecastle.moviedb.api.TheMovieDBInterface
import de.mobilecastle.moviedb.data.MovieDetails
import de.mobilecastle.moviedb.utilities.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// The API call done with this class using Rxjava and the API will return the movie details
// which will than assigned to the live data.
// CompositeDisposable is a RxJava component which help to dispose API calls
// for example disposing a running thread
class MovieDetailsDataSource(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _movieDetalsResponse = MutableLiveData<MovieDetails>()
    val movieDetalsResponse: LiveData<MovieDetails>
        get() = _movieDetalsResponse

    fun fetchMovieDetails(movieId: Int) {

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _movieDetalsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsNetworkDS", it.message.toString())
                        }
                    )
            )
        } catch (e: Exception) {
            Log.e("MovieDetailsNetworkDS", e.message.toString())
        }
    }
}