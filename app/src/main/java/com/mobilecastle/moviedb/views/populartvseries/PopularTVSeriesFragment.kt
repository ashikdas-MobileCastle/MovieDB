package com.mobilecastle.moviedb.views.populartvseries

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobilecastle.moviedb.R

class PopularTVSeriesFragment : Fragment() {

    companion object {
        fun newInstance() = PopularTVSeriesFragment()
    }

    private lateinit var viewModel: PopularTVSeriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.popular_t_v_series_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PopularTVSeriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}