package com.brainybeam.moviedemo.activities

/**
 * Created by BrainyBeam on 07-Jan-19.
 */

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.brainybeam.moviedemo.R
import com.brainybeam.moviedemo.adapters.SearchAdapter
import com.brainybeam.moviedemo.baseStructures.BaseAppCompactActivity
import com.brainybeam.moviedemo.database.DBHelper
import com.brainybeam.moviedemo.databinding.ActivitySearchBinding
import com.brainybeam.moviedemo.interfaces.ItemClickInterface
import com.brainybeam.moviedemo.models.SearchData

class SearchActivity : BaseAppCompactActivity(), ItemClickInterface {

    private lateinit var binding: ActivitySearchBinding
    private val db = DBHelper(this, null, null)
    private var listRecentSearch = arrayListOf<SearchData>()
    private val searchLimit = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutView(R.layout.activity_search)

        binding = getViewBinding()
        getRecentSearch()
        setSearchEvents()
    }

    override fun onItemClick(view: View, position: Int, obj: Any?) {
        val searchData: SearchData = obj as SearchData
        searchData.timestamp = System.currentTimeMillis()
        db.addSearchHistory(searchData)
        getRecentSearch()
        MovieListActivity.start(this, searchData.title)
    }

    private fun getRecentSearch() {
        listRecentSearch = db.getSearchHistory(searchLimit)

        if (binding.searchAdapter == null) {
            binding.searchAdapter = SearchAdapter(listRecentSearch, this)
        } else {
            binding.searchAdapter!!.refreshData(listRecentSearch)
        }
    }

    private fun setSearchEvents() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(searchText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(searchText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (searchText.isNullOrBlank()) {
                    binding.ivClearSearch.visibility = View.GONE
                } else {
                    binding.ivClearSearch.visibility = View.VISIBLE
                }
            }
        })

        binding.edtSearch.setOnEditorActionListener { p0, actionId, p2 ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH &&
                !TextUtils.isEmpty(binding.edtSearch.text.toString().trim())) {
                val searchData = SearchData()
                searchData.title = binding.edtSearch.text.toString()
                searchData.timestamp = System.currentTimeMillis()
                db.addSearchHistory(searchData)
                getRecentSearch()

                MovieListActivity.start(this@SearchActivity, searchData.title)
            }
            true
        }
    }

    fun onClick(view: View) {
        if (view.id == R.id.iv_clear_search) {
            binding.edtSearch.text.clear()
        } else if (view.id == R.id.iv_back_feed_search) {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

}