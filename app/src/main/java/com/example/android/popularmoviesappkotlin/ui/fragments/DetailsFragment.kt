package com.example.android.popularmoviesappkotlin.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.android.popularmoviesappkotlin.R
import com.example.android.popularmoviesappkotlin.data.Repository
import com.example.android.popularmoviesappkotlin.data.database.MovieDao
import com.example.android.popularmoviesappkotlin.data.database.MovieDatabase
import com.example.android.popularmoviesappkotlin.data.models.Movie
import com.example.android.popularmoviesappkotlin.data.network.MovieApiService
import com.example.android.popularmoviesappkotlin.data.network.MovieRetrofit
import com.example.android.popularmoviesappkotlin.utils.Constants
import com.example.android.popularmoviesappkotlin.viewmodel.DetailsViewModel
import com.example.android.popularmoviesappkotlin.viewmodel.FavouriteViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details_content.view.*

private const val ARG_PARAM1 = "movieId"

class DetailsFragment : Fragment() {
    private var movieId: Int = 0
    private lateinit var viewModel: DetailsViewModel

    companion object {
        fun newInstance(param1: Int) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM1, param1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(ARG_PARAM1)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (movieId == -1) {
            Toast.makeText(activity, getString(R.string.error_general), Toast.LENGTH_SHORT).show()
            activity!!.supportFragmentManager.popBackStack()
        }

        val application = activity!!.application
        val retrofitService = MovieRetrofit.getInstance()?.create(MovieApiService::class.java)!!
        val movieDao: MovieDao = MovieDatabase.getInstance(application).movieDao()
        val repository: Repository = Repository.getInstance(retrofitService, movieDao)

        viewModel = ViewModelProvider(
            this,
            DetailsViewModel.DetailsViewModelFactory(application, repository, movieId)
        ).get(DetailsViewModel::class.java)

        viewModel.movieDetails.observe(viewLifecycleOwner, Observer<Movie> { movie ->
            if (movie != null) {
                if (activity!!.actionBar != null) {
                    activity!!.actionBar!!.setDisplayHomeAsUpEnabled(true)
                    activity!!.actionBar!!.title = movie.title
                }
                Glide.with(this)
                    .load(Constants.BACKDROP_URL + movie.backdropPath)
                    .into(ivHeaderPoster)
                Glide.with(this)
                    .load(Constants.IMAGE_URL + movie.posterPath)
                    .into(extraDetails.ivPoster)
                extraDetails.tvOriginalTitle.text = movie.title
                extraDetails.tvReleaseDate.text = movie.releaseDate
                extraDetails.tvVoteAverage.text = movie.voteAverage.toString() + "/10"
                extraDetails.tvOverview.text = movie.overview
                extraDetails.tvLanguage.text = movie.originalLanguage
            }
        })

        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, getString(R.string.error_network), Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity!!.menuInflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                viewModel.movieDetails.observe(this, Observer<Movie> { movie ->
                    if (movie != null) {
                        val application = activity!!.application
                        val retrofitService =
                            MovieRetrofit.getInstance()?.create(MovieApiService::class.java)!!
                        val movieDao: MovieDao = MovieDatabase.getInstance(application).movieDao()
                        val repository: Repository = Repository.getInstance(
                            retrofitService,
                            movieDao
                        )

                        val favouriteViewModel: FavouriteViewModel = ViewModelProvider(
                            activity!!,
                            FavouriteViewModel.FavouriteViewModelFactory(application, repository)
                        ).get(FavouriteViewModel::class.java)

                        favouriteViewModel.insertFavouriteMovie(movie)
                    }
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}