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
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.database.MovieDao
import com.example.android.popularmoviesappkotlin.data.database.MovieDatabase
import com.example.android.popularmoviesappkotlin.data.models.Movie
import com.example.android.popularmoviesappkotlin.data.network.MovieApiService
import com.example.android.popularmoviesappkotlin.data.network.MovieRetrofit
import com.example.android.popularmoviesappkotlin.ui.activities.DetailsActivity
import com.example.android.popularmoviesappkotlin.ui.adapters.MovieAdapter
import com.example.android.popularmoviesappkotlin.viewmodel.FavouriteViewModel
import com.example.android.popularmoviesappkotlin.viewmodel.FavouriteViewModel.FavouriteViewModelFactory
import kotlinx.android.synthetic.main.fragment_movies_list.*

class FavouriteFragment : Fragment(), MovieAdapter.OnMovieClickListener {

    private lateinit var mFavourtieMovieAdapter: MovieAdapter
    private lateinit var viewModel: FavouriteViewModel

    companion object {
        private const val NUMBER_OF_COLUMNS = 3
        const val INTENT_EXTRA_MOVIE_ID = "MOVIE_ID"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewMovies.layoutManager = GridLayoutManager(activity, NUMBER_OF_COLUMNS)
        recyclerViewMovies.setHasFixedSize(true)
        mFavourtieMovieAdapter = MovieAdapter(this)
        recyclerViewMovies.adapter = mFavourtieMovieAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = activity!!.application
        val retrofitService = MovieRetrofit.getInstance()?.create(MovieApiService::class.java)!!
        val movieDao: MovieDao = MovieDatabase.getInstance(application).movieDao()
        val repository: Repository = Repository.getInstance(retrofitService, movieDao)

        viewModel = ViewModelProvider(this , FavouriteViewModelFactory(application, repository)).get(FavouriteViewModel::class.java)
        viewModel.getAllFavouriteMovies().observe(viewLifecycleOwner, Observer<List<Movie>> { movies ->
                if (movies != null && movies.isNotEmpty()) {
                    mFavourtieMovieAdapter.movies = movies
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
