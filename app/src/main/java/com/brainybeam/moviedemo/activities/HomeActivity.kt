package com.brainybeam.moviedemo.activities

/**
 * Created by BrainyBeam on 05-Jan-19.
 * @author BrainyBeam
 *
 * This is Activity class for Home screen.
 * This activity retrieve data from server and display in viewpager.
 */

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.raw_home_movie.view.*

class HomeActivity : BaseAppCompactActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var homePagerAdapter: HomePagerAdapter
    private val handler = Handler()
    private val autoSwipeTime: Long = 5000 //5 Second
    private var pageNo = 0
    private var isSwiping = false
    private lateinit var runnable: Runnable

    /**
     * This method is called when activity is created on screen.
     */
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

    /**
     * This method is called when activity is resume to foreground.
     * Auto Swiping timer is started when screen comes to foreground.
     */
    override fun onResume() {
        super.onResume()
        startSwiping()
    }

    /**
     * This method is called when activity is paused and screen goes to background.
     * Auto Swiping timer is stopped when screen goes to background.
     */
    override fun onPause() {
        stopSwiping()
        super.onPause()
    }

    /**
     * This method create and start timer for auto swipe.
     */
    private fun setRunnable() {
        runnable = object : Runnable {
            override fun run() {
                if (::homePagerAdapter.isInitialized) {
                    if (homePagerAdapter.count == pageNo+1) {
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

    /**
     * Define page swipe listener.
     * If user interacts movie list, stop timer. If user stop interacting movie list, start timer.
     */
    private fun setPagerListener() {
        binding.viewPagerMovie.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
                pageNo = position
                changeMovie(pageNo)

                binding.viewPagerMovie.btn_buy_ticket.animate().alpha(1.0f).duration = 300
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

    /**
     * This method retrieve data from movie list API call.
     * If data is received from server, display in movie listing and start auto swipe timer.
     */
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
                            setPageTransformer(true, HomePagerTransformation(this@HomeActivity))
                            startSwiping()
                        }
                        pageNo = 0
                        changeMovie(pageNo)
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

    /**
     * When user select any option item in context menu, this method is called.
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_search) {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Starts auto swipe timer.
     */
    private fun startSwiping() {
        if (!isSwiping) {
            isSwiping = true
            handler.postDelayed(runnable, autoSwipeTime)
        }
    }

    /**
     * Stop auto swipe timer.
     */
    private fun stopSwiping() {
        isSwiping = false
        handler.removeCallbacks(runnable)
    }

    private fun changeMovie(page: Int){
        val animation1 = AlphaAnimation(1.0f, 0.5f)
        animation1.duration = 1000
        animation1.startOffset = 5000
        animation1.fillAfter = true
        binding.tvMovieTitle.startAnimation(animation1)
        binding.tvMovieGenre.startAnimation(animation1)
        binding.tvMovieGenre.text = homePagerAdapter.getGenre(page)
        binding.tvMovieTitle.text = homePagerAdapter.getTitle(page)
    }

}
