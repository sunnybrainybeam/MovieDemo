package com.brainybeam.moviedemo.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.brainybeam.moviedemo.R
import com.brainybeam.moviedemo.adapters.MovieListPagerAdapter
import com.brainybeam.moviedemo.baseStructures.BaseAppCompactActivity
import com.brainybeam.moviedemo.databinding.ActivityMovieListBinding
import com.brainybeam.moviedemo.fragments.ComingMovieFragment
import com.brainybeam.moviedemo.fragments.ShowingMovieFragment

class MovieListActivity : BaseAppCompactActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MovieListActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityMovieListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutView(R.layout.activity_movie_list)
        binding = getViewBinding()
        setToolbar(binding.toolbarMovieList, getString(R.string.movie_list), true)
        setUpMovieListPager()
    }

    private fun setUpMovieListPager() {
        binding.tabMovie.setupWithViewPager(binding.viewPagerMovieList)
        val movieListPagerAdapter = MovieListPagerAdapter(supportFragmentManager)
        movieListPagerAdapter.addFragment(ShowingMovieFragment(), getString(R.string.now_showing))
        movieListPagerAdapter.addFragment(ComingMovieFragment(), getString(R.string.coming_soon))
        binding.viewPagerMovieList.adapter = movieListPagerAdapter
    }
}
