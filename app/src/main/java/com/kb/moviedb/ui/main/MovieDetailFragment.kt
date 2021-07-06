package com.kb.moviedb.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kb.moviedb.R
import com.kb.moviedb.databinding.MovieDetailFragmentBinding
import com.kb.moviedb.model.Genres
import com.kb.moviedb.model.MovieDetailResponse
import com.kb.moviedb.model.TagList
import com.kb.moviedb.network.MovieRepository
import com.kb.moviedb.network.MovieViewModelFactory
import com.kb.moviedb.utils.Constants
import com.kb.moviedb.utils.Constants.Companion.POSITION_KEY
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
        position?.let { viewModel.initialize(it) }
        setupObservers()
    }

    private fun setupObservers() {
        with(viewModel) {
            movieDetail.observe(viewLifecycleOwner, Observer {
                setMovieDetails(it)
            })
        }
    }

    private fun setMovieDetails(details: MovieDetailResponse) {
        tv_title.text = details.title
        tv_tagline.text = details.tagline

        if (!details.release_date.isNullOrEmpty()) {
            tv_date.text = getMonthYear(details.release_date)
        } else {
            tv_date.isGone = true
        }
        tv_rating.text = details.vote_average.toString()
        tv_duration.text = getDuration(details.runtime)
        tv_overview.text = details.overview
        tags?.let { tags ->
            if (details.genres?.isEmpty() == false) {
                tags.tagTextColor = Color.WHITE
                tags.crossColor = Color.WHITE
                val productNameList: List<String>? =
                    details.genres.let { genres -> genres.map { it.name } }

                tags.setTags(productNameList)
            } else {
                tags.isVisible = false
            }
        }

        activity?.let {
            Glide.with(it).load(Constants.BASE_POSTER_URL + details.backdrop_path)
                .into(bg_poster)
        }
    }

    private fun getDuration(minutes: Int?): String {
        minutes?.let { min ->
            val h: String = (min / 60).toString()
            val m: String = (min % 60).toString()
            return h.plus(HR).plus(m).plus(MIN)
        }
        return "0"
    }

    private fun getMonthYear(date: String?): String {
        date?.let {
            val formater = SimpleDateFormat(
                Constants.RECEIVING_DATE_FORMAT
            )
            val date = formater.parse(it)
            val calendar = Calendar.getInstance()
            calendar.setTime(date)

            val month =
                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) ?: BLANK_DATA
            val year =
                calendar.get(Calendar.YEAR)
            return month.plus(COMMA).plus(year)
        }
        return BLANK_DATA
    }

    private fun getTagColor(list: List<Genres>?): List<TagList> {
        //TODO - CHANGE TAG COLOR BASED ON GENRE
        val tagList = mutableListOf<TagList>()
        list?.let { genres ->
            for (s in genres) {
                val color = when (s.id) {
                    COMEDY -> R.color.white_transparent
                    CRIME -> Color.BLACK
                    ADVENTURE -> Color.GREEN
                    ADULT -> Color.RED
                    else -> R.color.white_transparent
                }
                tagList.add(
                    TagList(
                        color = color,
                        name = s.name
                    )
                )
            }
        }
        return tagList
    }

    companion object {
        private const val BLANK_DATA = ""
        private const val COMMA = ", "
        private const val HR = "h "
        private const val MIN = "m "

        internal const val COMEDY = 35
        internal const val CRIME = 80
        internal const val ADVENTURE = 28
        internal const val ADULT = 53
    }
}