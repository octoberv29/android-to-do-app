package com.example.android.popularmoviesappkotlin.ui.adapters

import android.content.Context
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

class MovieAdapter(
        private var movies: List<Movie>?,
        private var listener: OnMovieClickListener
    ) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private lateinit var context: Context;

    interface OnMovieClickListener {
        fun onClick(movieId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        context = parent.context // ?????
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = movies!![position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return if (movies != null) movies!!.size else 0
    }

    fun swapData(movies: List<Movie>?) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bind(movie: Movie) {
            Glide.with(context).load(Constants.IMAGE_URL + movie.getPosterPath()).into(itemView.ivThumbnail)
            itemView.tvOriginalTitle.setText(movie.getOriginalTitle())
            itemView.tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()))
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            val movieId: Long = movies!![position].getId()
            listener.onClick(movieId)
        }
    }
}