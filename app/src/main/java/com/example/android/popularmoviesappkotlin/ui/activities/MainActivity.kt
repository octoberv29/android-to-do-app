package com.example.android.popularmoviesappkotlin.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.android.popularmoviesappkotlin.R
import com.example.android.popularmoviesappkotlin.ui.fragments.DiscoverMoviesFragment
import com.example.android.popularmoviesappkotlin.ui.fragments.FavouriteMoviesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceByDiscoverMoviesFragment("popularity.desc")

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_trending -> {
                    replaceByDiscoverMoviesFragment("popularity.desc")
                    true
                }
                R.id.action_favourite -> {
                    replaceByFavouriteMoviesFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceByDiscoverMoviesFragment(sortBy: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, DiscoverMoviesFragment.newInstance(sortBy))
            .commit()
    }

    private fun replaceByFavouriteMoviesFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, FavouriteMoviesFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return when (id) {
            R.id.action_popular_movies -> {
                replaceByDiscoverMoviesFragment("popularity.desc")
                true
            }
            R.id.action_top_rated -> {
                replaceByDiscoverMoviesFragment("vote_average.desc")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}