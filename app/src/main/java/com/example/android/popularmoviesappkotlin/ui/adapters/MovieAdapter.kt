package com.example.android.popularmoviesappkotlin.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.popularmoviesappkotlin.R
import com.example.android.popularmoviesappkotlin.data.models.Movie
import com.example.android.popularmoviesappkotlin.utils.Constants
import kotlinx.android.synthetic.main.movie_item.view.*
import java.lang.String

class MovieAdapter(private val listener: OnMovieClickListener) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movies = listOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnMovieClickListener {
        fun onClick(movieId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = movies[position]
        holder.bind(movie, listener)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie, listener: OnMovieClickListener) {
            Glide.with(itemView.context)
                .load(Constants.IMAGE_URL + movie.posterPath)
                .into(itemView.ivThumbnail)
            itemView.tvOriginalTitle.text = movie.originalTitle
            itemView.tvVoteAverage.text = String.valueOf(movie.voteAverage)
            itemView.setOnClickListener {
                listener.onClick(movie.id!!)
            }
        }
    }
}