package de.mobilecastle.mvvmsampleapp.view.MovieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import de.mobilecastle.moviedb.data.Movie
import de.mobilecastle.moviedb.repository.MoviePagedListRepository
import de.mobilecastle.moviedb.utilities.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(
    private val moviePagedListRepository: MoviePagedListRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    // lazy used so that movieDetails can be created when needed not when the viewmodel initialized
    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        moviePagedListRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        moviePagedListRepository.getNetworkState()
    }

    fun isListEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    // onCleared get called when the activity or fragment get destroyed, so to prevent memory leaks
    // compositeDisposable.dispose() called here
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}