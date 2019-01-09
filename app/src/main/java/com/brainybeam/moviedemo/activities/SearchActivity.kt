package com.brainybeam.moviedemo.activities

/**
* Created by BrainyBeam on 05-Jan-19.
* @author BrainyBeam
*
* This is Activity class for Search screen.
* Manage UI and data in Search screen. Interacts with local database.
*/

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.brainybeam.moviedemo.R
import com.brainybeam.moviedemo.adapters.SearchAdapter
import com.brainybeam.moviedemo.baseStructures.BaseAppCompactActivity
import com.brainybeam.moviedemo.database.DBHelper
import com.brainybeam.moviedemo.databinding.ActivitySearchBinding
import com.brainybeam.moviedemo.interfaces.ItemClickInterface
import com.brainybeam.moviedemo.models.SearchData
import com.brainybeam.moviedemo.viewUtils.SwipeToDeleteCallback

class SearchActivity : BaseAppCompactActivity(), ItemClickInterface {

    private lateinit var binding: ActivitySearchBinding
    private val db = DBHelper(this, null, null)
    private var listRecentSearch = arrayListOf<SearchData>()
    private val searchLimit = 10

    /**
     * This method is called when activity is created on screen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutView(R.layout.activity_search)

        binding = getViewBinding()
        getRecentSearch()
        setSearchEvents()
        setSwipeDelete()
    }

    /**
     * This method is called when user clicks on any recent search list item.
     * Navigate user to Movie list screen.
     * Update database with search history.
     */
    override fun onItemClick(view: View, position: Int, obj: Any?) {
        val searchData: SearchData = obj as SearchData
        searchData.timestamp = System.currentTimeMillis()
        db.addSearchHistory(searchData)
        getRecentSearch()
        MovieListActivity.start(this, searchData.title)
    }

    /**
     * Retrieves recent search keywords from database and display in recent search list.
     */
    private fun getRecentSearch() {
        listRecentSearch = db.getSearchHistory(searchLimit)

        if (binding.searchAdapter == null) {
            binding.searchAdapter = SearchAdapter(listRecentSearch, this)
        } else {
            binding.searchAdapter!!.refreshData(listRecentSearch)
        }

        if (listRecentSearch.isEmpty())
            binding.rcvSearchHistory.visibility = View.GONE
        else
            binding.rcvSearchHistory.visibility = View.VISIBLE
    }

    /**
     * This method is called when user swipe to delete in recent search list.
     * Remove that search keyword from list and database.
     */
    private fun setSwipeDelete() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                db.deleteSearchHistory(listRecentSearch[position].title)
                listRecentSearch.removeAt(position)
                binding.searchAdapter!!.removeAt(position, listRecentSearch)
                if (listRecentSearch.isEmpty()) {
                    binding.rcvSearchHistory.visibility = View.GONE
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rcvSearchHistory)
    }

    /**
     * This method defines search EditText text change listener and Keyboard search key listener.
     */
    private fun setSearchEvents() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(view: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, startIndex: Int, endIndex: Int, offsets: Int) {
            }
            override fun onTextChanged(searchText: CharSequence?, startIndex: Int, endIndex: Int, offsets: Int) {
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

    /**
     * This method is defined as click listener in binding.
     * Handles click for back button and clear search icon.
     */
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