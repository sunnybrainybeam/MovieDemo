package com.brainybeam.moviedemo.activities

/**
 * Created by BrainyBeam on 07-Jan-19.
 */

import android.os.Bundle
import android.view.View
import com.brainybeam.moviedemo.R
import com.brainybeam.moviedemo.adapters.SearchAdapter
import com.brainybeam.moviedemo.baseStructures.BaseAppCompactActivity
import com.brainybeam.moviedemo.database.DBHelper
import com.brainybeam.moviedemo.databinding.ActivitySearchBinding
import com.brainybeam.moviedemo.interfaces.ItemClickInterface
import com.brainybeam.moviedemo.models.SearchData

class SearchActivity : BaseAppCompactActivity(), ItemClickInterface {
    
    private lateinit var binding: ActivitySearchBinding

    companion object {
        private var listRecentSearch = arrayListOf<SearchData>()
        private const val SEARCH_LIMIT = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutView(R.layout.activity_search)

        getRecentSearch()
        binding.searchAdapter = SearchAdapter(listRecentSearch, this)
    }

    override fun onItemClick(view: View, position: Int, obj: Any?) {
        val searchData: SearchData = obj as SearchData
        MovieListActivity.start(this , searchData.title)
    }

    private fun getRecentSearch(){
        var db = DBHelper(this, null, null)
        listRecentSearch = db.getSearchHistory(SEARCH_LIMIT)
    }

}