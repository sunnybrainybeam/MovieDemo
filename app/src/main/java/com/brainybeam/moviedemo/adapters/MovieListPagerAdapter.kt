package com.brainybeam.moviedemo.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

/**
 * Created by BrainyBeam on 05-Jan-19.
 */
class MovieListPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val listFragment = ArrayList<Fragment>()
    private val listTitle = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitle[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        listFragment.add(fragment)
        listTitle.add(title)
    }
}