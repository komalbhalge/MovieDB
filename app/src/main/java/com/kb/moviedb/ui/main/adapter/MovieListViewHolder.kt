package com.kb.moviedb.ui.main.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kb.moviedb.R

class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val flowerTextView: TextView = itemView.findViewById(R.id.movie_title)
    fun bind(word: String){
        flowerTextView.text = word
    }
}