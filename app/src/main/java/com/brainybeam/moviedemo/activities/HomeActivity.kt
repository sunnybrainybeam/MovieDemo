package com.brainybeam.moviedemo.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
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
    private val handler = Handler()
    private val autoSwipeTime: Long = 5000 //5 Second
    private var pageNo = 0
    private var isSwiping = false
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutView(R.layout.activity_home)
        binding = getViewBinding()
        setToolbar(binding.toolbarHome, getString(R.string.movie), false)
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        setRunnable()
        setPagerListener()
        callHomeMovieListApi()
    }

    override fun onResume() {
        super.onResume()
        startSwiping()
    }

    override fun onPause() {
        stopSwiping()
        super.onPause()
    }

    private fun setRunnable() {
        runnable = object : Runnable {
            override fun run() {
                if (::homePagerAdapter.isInitialized) {
                    if (homePagerAdapter.count == pageNo) {
                        pageNo = 0
                    } else {
                        pageNo++
                    }
                    binding.viewPagerMovie.setCurrentItem(pageNo, true)
                }
                handler.postDelayed(this, autoSwipeTime)
            }
        }
    }

    private fun setPagerListener() {
        binding.viewPagerMovie.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                pageNo = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    stopSwiping()
                } else {
                    startSwiping()
                }
            }
        })
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
                            startSwiping()
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
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startSwiping() {
        if (!isSwiping) {
            isSwiping = true
            handler.postDelayed(runnable, autoSwipeTime)
        }
    }

    private fun stopSwiping() {
        isSwiping = false
        handler.removeCallbacks(runnable)
    }
}
