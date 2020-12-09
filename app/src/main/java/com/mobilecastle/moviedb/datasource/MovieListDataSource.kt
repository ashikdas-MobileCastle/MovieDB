package de.mobilecastle.moviedb.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import de.mobilecastle.moviedb.api.TheMovieDBInterface
import de.mobilecastle.moviedb.data.Movie
import de.mobilecastle.moviedb.utilities.AppConstants.PAGE_BEGIN
import de.mobilecastle.moviedb.utilities.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieListDataSource (private val apiService: TheMovieDBInterface
                           , private val compositeDisposable: CompositeDisposable
                        ) : PageKeyedDataSource<Int, Movie>() {

    private var page = PAGE_BEGIN
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList, null, page+1)
                        Log.e("PageNumber", ""+page)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message.toString())
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovies(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key) {
                            callback.onResult(it.movieList, params.key+1)
                            Log.e("KEY", ""+params.key)
                            _networkState.postValue(NetworkState.LOADED)
                        }
                        else{
                            _networkState.postValue(NetworkState.EOL)
                        }
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message.toString())
                    }
                )
        )
    }
}