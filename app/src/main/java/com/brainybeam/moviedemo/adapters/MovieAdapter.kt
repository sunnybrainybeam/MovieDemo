package com.brainybeam.moviedemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brainybeam.moviedemo.R
import com.brainybeam.moviedemo.baseStructures.BaseViewHolder
import com.brainybeam.moviedemo.databinding.RawLoadingBinding
import com.brainybeam.moviedemo.databinding.RawMovieListBinding
import com.brainybeam.moviedemo.models.MovieData
import com.brainybeam.moviedemo.utility.Utility

/**
 * Created by BrainyBeam on 05-Jan-19.
 * @author BrainyBeam
 *
 * This is custom adapter class for movie listing screen with paging.
 * display movie list for both now showing movie list and upcoming movie list.
 */
class MovieAdapter(listMovie: ArrayList<MovieData?>) : RecyclerView.Adapter<BaseViewHolder>() {

    private var listMovie: ArrayList<MovieData?>? = listMovie
    private val VIEW_TYPE_MOVIE = 1
    private val VIEW_TYPE_LOADING = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == VIEW_TYPE_MOVIE) {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.raw_movie_list, parent, false))
        } else {
            LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.raw_loading, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return if (listMovie == null) 0 else listMovie!!.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val movieData = listMovie!![position]
            val binding: RawMovieListBinding = holder.getBinding()
            binding.movieData = movieData
            binding.tvDate.text = Utility.getDate(movieData!!.releaseDate)
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val movieData = listMovie!![position]
        return if (movieData == null) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_MOVIE
        }
    }

    fun addMovieData(listMovie: ArrayList<MovieData?>) {
        this.listMovie!!.addAll(listMovie)
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        listMovie!!.add(null)
        notifyItemInserted(listMovie!!.size + 1)
    }

    fun removeLoadingView() {
        listMovie!!.removeAt(listMovie!!.size - 1)
        notifyItemRemoved(listMovie!!.size)
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private var binding: RawMovieListBinding = getBinding()
    }

    inner class LoadingViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private var binding: RawLoadingBinding = getBinding()
    }
}