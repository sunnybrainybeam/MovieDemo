package com.brainybeam.moviedemo.baseStructures

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by BrainyBeam on 05-Jan-19.
 * @author BrainyBeam
 */
open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ViewDataBinding

    init {
        setBinding(itemView)
    }

    public fun <T : ViewDataBinding> getBinding(): T {
        return binding as T
    }

    private fun setBinding(itemView: View) {
        binding = DataBindingUtil.bind(itemView)!!
    }
}
