package de.mobilecastle.moviedb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.mobilecastle.moviedb.data.MovieDetails
import de.mobilecastle.moviedb.repository.MovieDetailsRepository
import de.mobilecastle.moviedb.utilities.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel(
    private val movieDetailsRepository: MovieDetailsRepository,
    movieId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    // lazy used so that movieDetails can be created when needed not when the viewmodel initialized
    val movieDetails: LiveData<MovieDetails> by lazy {
        movieDetailsRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieDetailsRepository.getMovieDetailsNetworkState()
    }

    // onCleared get called when the activity or fragment get destroyed, so to prevent memory leaks
    // compositeDisposable.dispose() called here
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}