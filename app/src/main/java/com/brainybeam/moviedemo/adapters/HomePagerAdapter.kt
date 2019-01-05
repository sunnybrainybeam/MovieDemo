package com.brainybeam.moviedemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.brainybeam.moviedemo.R
import com.brainybeam.moviedemo.databinding.RawHomeMovieBinding
import com.brainybeam.moviedemo.models.GenreIds
import com.brainybeam.moviedemo.models.MovieData
import java.lang.StringBuilder

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
class HomePagerAdapter(private val movieList: ArrayList<MovieData>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: RawHomeMovieBinding =
            DataBindingUtil.inflate(LayoutInflater.from(container.context), R.layout.raw_home_movie, container, false)
        binding.movieData = movieList[position]
        setGenre(binding, position)
        binding.executePendingBindings()
        container.addView(binding.root)
        return binding.root
    }

    private fun setGenre(binding: RawHomeMovieBinding, position: Int) {
        val genre = StringBuilder("")
        for (movieData in movieList[position].genreIds) {
            if (genre.length > 1) {
                genre.append(" | ")
            }
            genre.append(movieData.name)
        }
        binding.tvMovieGenre.text = genre.toString()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return movieList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}