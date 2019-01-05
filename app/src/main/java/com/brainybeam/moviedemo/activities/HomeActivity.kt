package com.brainybeam.moviedemo.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brainybeam.moviedemo.R
import com.brainybeam.moviedemo.adapters.HomePagerAdapter
import com.brainybeam.moviedemo.baseStructures.BaseAppCompactActivity
import com.brainybeam.moviedemo.databinding.ActivityHomeBinding
import com.brainybeam.moviedemo.models.MovieData
import com.brainybeam.moviedemo.utility.Constant
import com.brainybeam.moviedemo.utility.Utility
import com.brainybeam.moviedemo.viewModels.MovieViewModel
import com.brainybeam.moviedemo.viewUtils.HomePagerTransformation

class HomeActivity : BaseAppCompactActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var homePagerAdapter: HomePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutView(R.layout.activity_home)
        binding = getViewBinding()
        setToolbar(binding.toolbarHome, getString(R.string.movie), false)
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        callHomeMovieListApi()
    }

    private fun callHomeMovieListApi() {
        if (Utility.isInternetConnected(this)) {
            binding.homeProgressBar.visibility = View.VISIBLE
            mMovieViewModel.getHomeMovieList().observe(this, Observer {
                if (it != null) {
                    if (it.success) {
                        homePagerAdapter = HomePagerAdapter(it.results as ArrayList<MovieData>)
                        binding.viewPagerMovie.apply {
                            adapter = homePagerAdapter
                            clipToPadding = false
                            setPadding(200, 50, 200, 50)
                            pageMargin = 100
                            setPageTransformer(false, HomePagerTransformation(this@HomeActivity))
                        }
                    } else {
                        Utility.showToastMessage(this@HomeActivity, it.message)
                    }
                    binding.homeProgressBar.visibility = View.GONE
                }
            })
        } else {
            Utility.showToastMessage(this, Constant.INTERNET_ERROR)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_search) {
            Utility.showToastMessage(this, getString(R.string.search))
        }
        return super.onOptionsItemSelected(item)
    }
}
