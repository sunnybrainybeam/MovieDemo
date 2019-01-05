package com.brainybeam.moviedemo.fragments

import android.os.Bundle
import com.brainybeam.moviedemo.R
import com.brainybeam.moviedemo.adapters.MovieAdapter
import com.brainybeam.moviedemo.baseStructures.BaseFragment
import com.brainybeam.moviedemo.databinding.FragmentComingMovieBinding

/**
 * Created by BrainyBeam on 05-Jan-19.
 */

class ComingMovieFragment : BaseFragment() {

    private lateinit var binding: FragmentComingMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.fragment_coming_movie)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = getBinding()
        binding.movieAdapter = MovieAdapter()
    }
}