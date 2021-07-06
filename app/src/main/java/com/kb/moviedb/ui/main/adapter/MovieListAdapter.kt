package com.kb.moviedb.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kb.moviedb.databinding.ItemMovieBinding
import com.kb.moviedb.model.Movie
import com.kb.moviedb.utils.Constants.Companion.BASE_POSTER_URL
import com.kb.moviedb.utils.Constants.Companion.DATE_FORMAT
import com.kb.moviedb.utils.Constants.Companion.NO_DATA
import com.kb.moviedb.utils.Constants.Companion.RECEIVING_DATE_FORMAT
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class MovieListAdapter(private val onItemClicked: (position: Int) -> Unit) :
    RecyclerView.Adapter<MovieViewHolder>() {

    var movieList = mutableListOf<Movie>()

    fun setMovies(movies: List<Movie>) {
        this.movieList = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding, movieList, onItemClicked)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        with(holder.binding) {
            tvMovieTitle.text = movie.title
            tvLikeCount.text = movie.voteCount.toString()
            tvRateCount.text = movie.voteAverage.toString()
            tvRelaseDate.text = RELEASING_ON.plus(getFormattedDate(movie.releaseDate))
        }

        Glide.with(holder.itemView.context).load(BASE_POSTER_URL + movie.backdropPath)
            .into(holder.binding.moviePoster)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private fun getFormattedDate(date: String?): String {
        date?.let {
            val parser = SimpleDateFormat(RECEIVING_DATE_FORMAT)
            val formatter = SimpleDateFormat(DATE_FORMAT)
            return formatter.format(parser.parse(date))
        }
        return NO_DATA
    }

    companion object {
        internal const val RELEASING_ON = "Releasing on "
    }

}


class MovieViewHolder(
    val binding: ItemMovieBinding,
    val movies: List<Movie>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val position = movies.get(bindingAdapterPosition).id
        position?.let { onItemClicked(it) }
    }
}
