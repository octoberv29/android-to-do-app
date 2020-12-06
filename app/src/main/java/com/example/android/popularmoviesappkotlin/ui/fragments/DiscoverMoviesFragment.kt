package com.example.android.popularmoviesappkotlin.ui.fragments

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android.popularmoviesappkotlin.data.models.Movie
import com.example.android.popularmoviesappkotlin.ui.activities.DetailsActivity
import com.example.android.popularmoviesappkotlin.ui.adapters.MovieAdapter
import com.example.android.popularmoviesappkotlin.viewmodel.DiscoverMoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies_list.*


class DiscoverMoviesFragment : Fragment(), MovieAdapter.OnMovieClickListener {

    private val NUMBER_OF_COLUMNS = 3
    val INTENT_EXTRA_MOVIE_ID = "MOVIE_ID"

    private var sortBy: String? = null
    private var mMovieAdapter: MovieAdapter? = null

    private lateinit var viewModel: DiscoverMoviesViewModel

    fun newInstance(sortBy: String?): DiscoverMoviesFragment? {
        val fragment = DiscoverMoviesFragment()
        val args = Bundle()
        args.putString("sortBy", sortBy)
        fragment.arguments = args
        return fragment
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
        return View.inflate(activity, R.layout.fragment_movies_list, container)
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
            DiscoverMoviesViewModel.MovieResponseViewModelFactory(
                this.activity!!.application,
                sortBy
            )
        ).get(
            DiscoverMoviesViewModel::class.java
        )
        if (sortBy != null) {
            viewModel.getMoviesLiveData().observe(this,
                Observer<List<Movie>> { movies ->
                    if (movies != null && movies.isNotEmpty()) {
                        mMovieAdapter?.swapData(movies)
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mMovieAdapter != null) {
            mMovieAdapter = null
        }
    }

    // for MovieAdapter.OnMovieClickListener
    override fun onClick(movieId: Long) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(INTENT_EXTRA_MOVIE_ID, movieId)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearCompositeDisposable()
    }
}