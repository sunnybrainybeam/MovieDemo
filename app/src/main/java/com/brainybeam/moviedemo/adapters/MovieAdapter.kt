package com.brainybeam.moviedemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brainybeam.moviedemo.R
import com.brainybeam.moviedemo.baseStructures.BaseViewHolder
import com.brainybeam.moviedemo.databinding.RawMovieListBinding

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
class MovieAdapter : RecyclerView.Adapter<BaseViewHolder> {

    constructor() {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.raw_movie_list, parent, false))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private var binding: RawMovieListBinding = getBinding()

        init {
            /*binding.llLeadBox.setOnClickListener { v ->
                itemClickInterface?.onItemClick(v, adapterPosition, listLeads!![adapterPosition])
            }*/
        }
    }
}