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
import com.example.android.popularmoviesappkotlin.ui.fragments.FavouriteMoviesFragment.Companion.INTENT_EXTRA_MOVIE_ID
import com.example.android.popularmoviesappkotlin.utils.Constants
import com.example.android.popularmoviesappkotlin.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details_content.view.*

class DetailsActivity : AppCompatActivity() {
    private var movieId: Long = 0

    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent = intent
        if (intent.hasExtra(INTENT_EXTRA_MOVIE_ID)) {

            movieId = intent.getLongExtra(INTENT_EXTRA_MOVIE_ID, -1)
            if (movieId == -1L) {
                Toast.makeText(this, "Sorry, error has occurred!", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }

            viewModel =
                ViewModelProvider(this, MovieViewModelFactory(this.application, movieId)).get(
                    MovieDetailsViewModel::class.java
                )

            viewModel.getMovieDetailsWithRx().observe(this,
                Observer<Any?> { movie ->
                    if (movie != null) {
                        if (supportActionBar != null) {
                            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                            supportActionBar.setTitle(movie.getTitle())
                        }
                        Glide.with(this@DetailsActivity)
                            .load(Constants.BACKDROP_URL + movie.getBackdropPath())
                            .into(ivHeaderPoster)
                        Glide.with(this@DetailsActivity)
                            .load(Constants.IMAGE_URL + movie.getPosterPath())
                            .into(extraDetails.ivPoster)
                        extraDetails.tvOriginalTitle.setText(movie.getTitle())
                        extraDetails.tvReleaseDate.setText(movie.getReleaseDate())
                        extraDetails.tvVoteAverage.setText(
                            movie.getVoteAverage().toString() + "/10"
                        )
                        extraDetails.tvOverview.setText(movie.getOverview())
                        extraDetails.tvLanguage.setText(movie.getOriginalLanguage())
                    }
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                viewModel.getMovieDetailsWithRx().observe(this,
                    Observer<Any?> { movie ->
                        if (movie != null) {
                            val favouriteViewModel: FavouriteMoviesViewModel =
                                ViewModelProvider(this@DetailsActivity).get(
                                    FavouriteMoviesViewModel::class.java
                                )
                            favouriteViewModel.insertFavouriteMovie(movie)
                        }
                    })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}