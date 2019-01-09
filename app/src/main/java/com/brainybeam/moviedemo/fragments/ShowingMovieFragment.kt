package com.brainybeam.moviedemo.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brainybeam.moviedemo.R
import com.brainybeam.moviedemo.activities.MovieListActivity
import com.brainybeam.moviedemo.adapters.MovieAdapter
import com.brainybeam.moviedemo.baseStructures.BaseFragment
import com.brainybeam.moviedemo.databinding.FragmentShowingMovieBinding
import com.brainybeam.moviedemo.models.MovieList
import com.brainybeam.moviedemo.utility.Constant
import com.brainybeam.moviedemo.utility.Utility
import com.brainybeam.moviedemo.viewModels.MovieViewModel
import com.brainybeam.moviedemo.viewUtils.RecyclerViewLoadMoreListener

/**
 * Created by BrainyBeam on 05-Jan-19.
 * @author BrainyBeam
 * This class is for Current Showing Movies fragment.
 */
class ShowingMovieFragment : BaseFragment() {

    private lateinit var binding: FragmentShowingMovieBinding
    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var mRecyclerViewLoadMoreList: RecyclerViewLoadMoreListener
    private var pageNo = 1

    /**
     * This method is called when Fragment is created on screen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.fragment_showing_movie)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = getBinding()
        setUp()
    }

    /**
     * This method setup pagination in movie list.
     */
    private fun setUp() {
        mRecyclerViewLoadMoreList = object : RecyclerViewLoadMoreListener() {
            override fun onLoadMore() {
                getShowingMovieList(pageNo++)
                binding.movieAdapter?.addLoadingView()
            }
        }
        binding.rcvShowingMovie.addOnScrollListener(mRecyclerViewLoadMoreList)
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        getShowingMovieList(pageNo)
    }

    /**
     * Retrieve upcoming movie list from list of all movies retrieved based on search.
     */
    private fun getShowingMovieList(pageNo: Int) {
        if (Utility.isInternetConnected(context!!)) {
            mMovieViewModel.getSearchMovieList((activity as MovieListActivity).keyword, pageNo).observe(this, Observer {
                if (it != null) {
                    binding.progressBarShowing.visibility = View.GONE
                    if (it.success) {
                        val movieList: MovieList = it.results as MovieList
                        if (binding.movieAdapter != null) {
                            binding.movieAdapter!!.removeLoadingView()
                            mRecyclerViewLoadMoreList.setLoaded()
                        }
                        if (binding.movieAdapter == null) {
                            binding.movieAdapter = MovieAdapter(movieList.showingMovieList)
                        } else {
                            binding.movieAdapter!!.addMovieData(movieList.showingMovieList)
                        }
                    } else {
                        Utility.showToastMessage(context!!, it.message)
                    }
                }
            })
        } else {
            binding.progressBarShowing.visibility = View.GONE
            Utility.showToastMessage(context!!, Constant.INTERNET_ERROR)
        }
    }
}
