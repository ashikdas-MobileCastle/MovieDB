package de.mobilecastle.moviedb.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import de.mobilecastle.moviedb.api.TheMovieDBInterface
import de.mobilecastle.moviedb.data.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieListDataSourceFactory (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieListDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieListDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}