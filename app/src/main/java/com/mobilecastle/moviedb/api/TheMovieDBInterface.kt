package de.mobilecastle.moviedb.api

import de.mobilecastle.moviedb.data.MovieDetails
import de.mobilecastle.moviedb.data.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//Request Response API interface
interface TheMovieDBInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=914d96331accb432f57e7ead8d050db2&page=1
    // https://api.themoviedb.org/3/movie/popular?api_key=914d96331accb432f57e7ead8d050db2&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=914d96331accb432f57e7ead8d050db2
    // https://api.themoviedb.org/3/

    // Observable is a data stream that done some work and init data
    // and Observer is a counter part of observable it receive the data it needed from the observable
    // Single is one of the observable
    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}