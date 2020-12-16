package com.example.android.popularmoviesappkotlin.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.example.android.popularmoviesappkotlin.ui.fragments.FavouriteFragment.Companion.INTENT_EXTRA_MOVIE_ID
import com.example.android.popularmoviesappkotlin.utils.Constants
import com.example.android.popularmoviesappkotlin.viewmodel.FavouriteViewModel
import com.example.android.popularmoviesappkotlin.viewmodel.DetailsViewModel
import com.example.android.popularmoviesappkotlin.viewmodel.DetailsViewModel.*
import com.example.android.popularmoviesappkotlin.viewmodel.FavouriteViewModel.FavouriteViewModelFactory
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details_content.view.*


class DetailsActivity : AppCompatActivity() {
    private var movieId: Int = 0

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent = intent
        if (intent.hasExtra(INTENT_EXTRA_MOVIE_ID)) {

            movieId = intent.getIntExtra(INTENT_EXTRA_MOVIE_ID, -1)

            if (movieId == -1) {
                Toast.makeText(this, getString(R.string.error_general), Toast.LENGTH_SHORT)
                    .show()
                onBackPressed()
            }

            val retrofitService = MovieRetrofit.getInstance()?.create(MovieApiService::class.java)!!
            val movieDao: MovieDao = MovieDatabase.getInstance(application).movieDao()
            val repository: Repository = Repository.getInstance(retrofitService, movieDao)

            viewModel = ViewModelProvider(
                this,
                DetailsViewModelFactory(this.application, repository, movieId)
            ).get(DetailsViewModel::class.java)

            viewModel.movieDetails.observe(this,
                Observer<Movie> { movie ->
                    if (movie != null) {
                        if (supportActionBar != null) {
                            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                            supportActionBar!!.title = movie.title
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

            viewModel.eventNetworkError.observe(this, Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
        }
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(
                this,
                getString(R.string.error_network),
                Toast.LENGTH_LONG
            ).show()
            viewModel.onNetworkErrorShown()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                viewModel.movieDetails.observe(this, Observer<Movie> { movie ->
                    if (movie != null) {
                        val retrofitService =
                            MovieRetrofit.getInstance()?.create(MovieApiService::class.java)!!
                        val movieDao: MovieDao = MovieDatabase.getInstance(application).movieDao()
                        val repository: Repository =
                            Repository.getInstance(retrofitService, movieDao)

                        val favouriteViewModel: FavouriteViewModel = ViewModelProvider(
                                this@DetailsActivity,
                                FavouriteViewModelFactory(application, repository)
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