package com.mobilecastle.moviedb.views.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mobilecastle.moviedb.R
import de.mobilecastle.moviedb.api.TheMovieDBClient
import de.mobilecastle.moviedb.api.TheMovieDBInterface
import de.mobilecastle.moviedb.data.MovieDetails
import de.mobilecastle.moviedb.repository.MovieDetailsRepository
import de.mobilecastle.moviedb.utilities.AppConstants.POSTER_BASE_URL
import de.mobilecastle.moviedb.utilities.NetworkState
import kotlinx.android.synthetic.main.content_movie.*
import kotlinx.android.synthetic.main.movie_details_fragment.*
import java.text.NumberFormat
import java.util.*

class MovieDetailsFragment : Fragment() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener(View.OnClickListener { view -> (activity as AppCompatActivity).onBackPressed() })
        val movieId: Int? = arguments?.getInt("movieId")

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = movieId?.let { getViewModel(it) }!!

        viewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            progress_bar_details.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    fun bindUI(it: MovieDetails) {
        toolbar_layout.title = it.title
        //movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.avgRating.toString()
        movie_duration.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);


    }


    private fun getViewModel(movieId: Int): MovieDetailsViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailsViewModel(movieRepository, movieId) as T
            }
        })[MovieDetailsViewModel::class.java]
    }
}