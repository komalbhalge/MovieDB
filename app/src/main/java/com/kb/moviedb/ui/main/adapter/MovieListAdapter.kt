package com.kb.moviedb.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kb.moviedb.databinding.ItemMovieBinding
import com.kb.moviedb.model.Movie

class MovieListAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    var movieList = mutableListOf<Movie>()

    fun setMovies(movies: List<Movie>) {
        this.movieList = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.binding.movieTitle.text = movie.title
        Glide.with(holder.itemView.context).load(BASE_POSTER_URL + movie.posterPath)
            .into(holder.binding.moviePoster)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    companion object {
        private var BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500/"
    }
}

class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)
