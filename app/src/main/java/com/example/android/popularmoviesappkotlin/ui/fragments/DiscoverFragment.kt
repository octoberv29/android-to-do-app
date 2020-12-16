package com.example.android.popularmoviesappkotlin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.popularmoviesappkotlin.R
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.database.MovieDao
import com.example.android.popularmoviesappkotlin.data.database.MovieDatabase
import com.example.android.popularmoviesappkotlin.data.models.MovieResponse
import com.example.android.popularmoviesappkotlin.data.network.MovieApiService
import com.example.android.popularmoviesappkotlin.data.network.MovieRetrofit
import com.example.android.popularmoviesappkotlin.ui.activities.DetailsActivity
import com.example.android.popularmoviesappkotlin.ui.adapters.MovieAdapter
import com.example.android.popularmoviesappkotlin.viewmodel.DiscoverViewModel
import com.example.android.popularmoviesappkotlin.viewmodel.DiscoverViewModel.*
import kotlinx.android.synthetic.main.fragment_movies_list.*


class DiscoverFragment : Fragment(), MovieAdapter.OnMovieClickListener {

    private val NUMBER_OF_COLUMNS = 3
    val INTENT_EXTRA_MOVIE_ID = "MOVIE_ID"

    private lateinit var sortBy: String
    private lateinit var mMovieAdapter: MovieAdapter

    private lateinit var viewModel: DiscoverViewModel

    companion object {
        fun newInstance(sortBy: String): DiscoverFragment {
            val fragment = DiscoverFragment()
            val args = Bundle()
            args.putString("sortBy", sortBy)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sortBy = if (arguments != null) arguments!!.getString("sortBy", "") else "popularity.desc"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewMovies.layoutManager = GridLayoutManager(activity, NUMBER_OF_COLUMNS)
        recyclerViewMovies.setHasFixedSize(true)
        mMovieAdapter = MovieAdapter(this)
        recyclerViewMovies.adapter = mMovieAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = activity!!.application
        val retrofitService = MovieRetrofit.getInstance()?.create(MovieApiService::class.java)!!
        val movieDao: MovieDao = MovieDatabase.getInstance(application).movieDao()
        val repository: Repository = Repository.getInstance(retrofitService, movieDao)

        viewModel = ViewModelProvider(this, DiscoverViewModelFactory(application, repository, sortBy, 1))
            .get(DiscoverViewModel::class.java)

        viewModel.movieResponse.observe(viewLifecycleOwner, Observer<MovieResponse> { movieResponse ->
                if (movieResponse != null) {
                    val movies = movieResponse.movies
                    if (movies != null && movies.isNotEmpty()) {
                        mMovieAdapter.movies = movies
                    }
                }
            })

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, R.string.error_network, Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    // for MovieAdapter.OnMovieClickListener
    override fun onClick(movieId: Int) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(INTENT_EXTRA_MOVIE_ID, movieId)
        startActivity(intent)
    }
}