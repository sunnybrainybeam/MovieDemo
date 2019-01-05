package com.brainybeam.moviedemo.utility

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brainybeam.moviedemo.baseStructures.BaseViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Created by BrainyBeam on 05-Jan-19.
 */


@BindingAdapter("bind:adapter")
fun recyclerAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<BaseViewHolder>?) {
    view.layoutManager = LinearLayoutManager(view.context)
    view.itemAnimator = DefaultItemAnimator()
    view.setHasFixedSize(false)
    view.adapter = adapter
}


@BindingAdapter("bind:image")
fun setImage(imageView: ImageView, url: String) {
    Glide.with(imageView)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}
