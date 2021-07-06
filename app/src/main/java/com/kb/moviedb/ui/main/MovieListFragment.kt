package com.kb.moviedb.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.kb.moviedb.R
import com.kb.moviedb.R.layout.movie_list_fragment
import com.kb.moviedb.databinding.MovieListFragmentBinding
import com.kb.moviedb.network.MovieRepository
import com.kb.moviedb.network.MovieViewModelFactory
import com.kb.moviedb.ui.main.adapter.MovieListAdapter
import com.kb.moviedb.utils.Constants.Companion.POSITION_KEY
import kotlinx.android.synthetic.main.movie_list_fragment.*

class MovieListFragment : Fragment() {

    private val TAG = MovieListFragment::class.qualifiedName
    private val adapter = MovieListAdapter{position -> onListItemClick(position) }
    private var movieListBinding: MovieListFragmentBinding? = null
    private lateinit var viewModel: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(movie_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = MovieListFragmentBinding.bind(view)
        movieListBinding = binding
        val mainRepository = MovieRepository.getInstance()
        binding.movieRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(mainRepository)
        ).get(MovieViewModel::class.java)
        viewModel.initialize()
        setupObservers()
    }

    override fun onDestroyView() {
        movieListBinding = null
        super.onDestroyView()
    }

    private fun setupObservers() {
        with(viewModel) {
            movieList.observe(viewLifecycleOwner, Observer {
                it.results?.let { adapter.setMovies(it) }
            })
        }
    }

    private fun onListItemClick(position: Int) {
        val bundle = bundleOf(POSITION_KEY to position)
        movieListBinding?.let { findNavController(it.root).navigate(R.id.movieDetailFragment, bundle) }
    }
}