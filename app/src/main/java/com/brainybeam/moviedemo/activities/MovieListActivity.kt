package com.brainybeam.moviedemo.activities

/**
 * Created by BrainyBeam on 07-Jan-19.
 * @author BrainyBeam
 *
 * This is Activity class for Searched movie listing screen.
 * This activity retrieve data based on user's search string from server and display in list.
 */

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

    private lateinit var binding: ActivityMovieListBinding
    var keyword: String = ""

    /**
     * Define static variables
     */
    companion object {
        fun start(context: Context, keyword: String) {
            val intent = Intent(context, MovieListActivity::class.java)
            intent.putExtra("KEYWORD", keyword)
            context.startActivity(intent)
        }
    }

    /**
     * This method is called when activity is called.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutView(R.layout.activity_movie_list)
        binding = getViewBinding()
        setToolbar(binding.toolbarMovieList, getString(R.string.movie_list), true)
        setUpMovieListPager()
        keyword = intent.getStringExtra("KEYWORD")
    }

    /**
     * This method setup ViewPager with 2 tabs.
     * Currently showing movies & Upcoming movies.
     */
    private fun setUpMovieListPager() {
        binding.tabMovie.setupWithViewPager(binding.viewPagerMovieList)
        val movieListPagerAdapter = MovieListPagerAdapter(supportFragmentManager)
        movieListPagerAdapter.addFragment(ShowingMovieFragment(), getString(R.string.now_showing))
        movieListPagerAdapter.addFragment(ComingMovieFragment(), getString(R.string.coming_soon))
        binding.viewPagerMovieList.adapter = movieListPagerAdapter
    }
}
