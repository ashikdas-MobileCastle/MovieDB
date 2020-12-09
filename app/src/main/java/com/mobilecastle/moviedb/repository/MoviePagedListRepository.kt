package de.mobilecastle.moviedb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import de.mobilecastle.moviedb.api.TheMovieDBInterface
import de.mobilecastle.moviedb.data.Movie
import de.mobilecastle.moviedb.datasource.MovieListDataSource
import de.mobilecastle.moviedb.datasource.MovieListDataSourceFactory
import de.mobilecastle.moviedb.utilities.AppConstants.POST_PER_PAGE
import de.mobilecastle.moviedb.utilities.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: TheMovieDBInterface) {

    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSourceFactory: MovieListDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieListDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieListDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieListDataSource::networkState
        )
    }
}