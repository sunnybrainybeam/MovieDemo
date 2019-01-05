package com.brainybeam.moviedemo.viewUtils

import android.content.Context
import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * Created by BrainyBeam on 05-Jan-19.
 */

class HomePagerTransformation(context: Context) : ViewPager.PageTransformer {

    private val maxOffsetX: Int
    private var viewPager: ViewPager? = null

    init {
        this.maxOffsetX = dp2px(context, 180f)
    }

    override fun transformPage(view: View, position: Float) {
        if (viewPager == null) {
            viewPager = view.parent as ViewPager
        }

        val leftInScreen = view.left - viewPager!!.scrollX
        val centerXInViewPager = leftInScreen + view.measuredWidth / 2
        val offsetX = centerXInViewPager - viewPager!!.measuredWidth / 2
        val offsetRate = offsetX.toFloat() * 0.38f / viewPager!!.measuredWidth
        val scaleFactor = 1 - Math.abs(offsetRate)
        if (scaleFactor > 0) {
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
            view.translationX = -maxOffsetX * offsetRate
        }
    }

    private fun dp2px(context: Context, dip: Float): Int {
        val m = context.resources.displayMetrics.density
        return (dip * m + 0.5f).toInt()
    }

}