package com.brainybeam.moviedemo.viewUtils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
abstract class RecyclerViewLoadMoreListener : RecyclerView.OnScrollListener() {

    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var visibleThreshold = 2
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var isLoading: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy <= 0) return
        if (mLayoutManager == null) {
            mLayoutManager = recyclerView.layoutManager
            if (mLayoutManager is GridLayoutManager) {
                visibleThreshold *= 2
            }
        }
        totalItemCount = mLayoutManager!!.getItemCount()

        when (mLayoutManager) {
            is LinearLayoutManager -> lastVisibleItem =
                    (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            is GridLayoutManager -> lastVisibleItem =
                    (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions =
                    (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                // get maximum element within the list
                lastVisibleItem = getLastVisibleItem(lastVisibleItemPositions)
            }
        }

        if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
            isLoading = true
            onLoadMore()
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray?): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions!!.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    fun setLoaded() {
        isLoading = false
    }

    abstract fun onLoadMore()
}