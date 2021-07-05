package com.kb.moviedb.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kb.moviedb.R
import com.kb.moviedb.databinding.MovieDetailFragmentBinding
import com.kb.moviedb.network.MovieRepository
import com.kb.moviedb.network.MovieViewModelFactory
import com.kb.moviedb.utils.Constants.Companion.POSITION_KEY

class MovieDetailFragment : Fragment() {
    private val TAG = MovieDetailFragment::class.qualifiedName
    private var position: Int? = 0
    private lateinit var viewModel: MovieDetailViewModel
    private var mBinding: MovieDetailFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_detail_fragment, container, false)
        position = arguments?.let { it.getInt(POSITION_KEY) }
        Log.e(TAG, "position: " + position)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = MovieDetailFragmentBinding.bind(view)
        mBinding = binding

        val mainRepository = MovieRepository.getInstance()
        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(mainRepository)
        ).get(MovieDetailViewModel::class.java)
        viewModel.initialize()
    }
}