package com.example.android.popularmoviesappkotlin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.popularmoviesappkotlin.R
import com.example.android.popularmoviesappkotlin.data.models.MovieResponse
import com.example.android.popularmoviesappkotlin.ui.activities.DetailsActivity
import com.example.android.popularmoviesappkotlin.ui.adapters.MovieAdapter
import com.example.android.popularmoviesappkotlin.viewmodel.DiscoverMoviesViewModel
import com.example.android.popularmoviesappkotlin.viewmodel.DiscoverMoviesViewModel.*
import kotlinx.android.synthetic.main.fragment_movies_list.*


class DiscoverMoviesFragment : Fragment(), MovieAdapter.OnMovieClickListener {

    private val NUMBER_OF_COLUMNS = 3
    val INTENT_EXTRA_MOVIE_ID = "MOVIE_ID"

    private lateinit var sortBy: String
    private lateinit var mMovieAdapter: MovieAdapter

    private lateinit var viewModel: DiscoverMoviesViewModel

    companion object {
        fun newInstance(sortBy: String): DiscoverMoviesFragment {
            val fragment = DiscoverMoviesFragment()
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewMovies.layoutManager = GridLayoutManager(activity, NUMBER_OF_COLUMNS)
        recyclerViewMovies.setHasFixedSize(true)
        mMovieAdapter = MovieAdapter(null, this)
        recyclerViewMovies.adapter = mMovieAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            DiscoverMoviesViewModelFactory(this.activity!!.application, sortBy)
        ).get(DiscoverMoviesViewModel::class.java)

        viewModel.movieResponseObservable.observe(viewLifecycleOwner, Observer<MovieResponse> { movieResponse ->
                if (movieResponse != null) {
                    val movies = movieResponse.movies
                    if (movies != null && movies.isNotEmpty()) {
                        mMovieAdapter.swapData(movies)
                    }
                }
            })
    }

    // for MovieAdapter.OnMovieClickListener
    override fun onClick(movieId: Int) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(INTENT_EXTRA_MOVIE_ID, movieId)
        startActivity(intent)
    }
}