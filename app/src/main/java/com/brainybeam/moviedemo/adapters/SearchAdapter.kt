package com.brainybeam.moviedemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brainybeam.moviedemo.R
import com.brainybeam.moviedemo.baseStructures.BaseViewHolder
import com.brainybeam.moviedemo.databinding.RawSearchListBinding
import com.brainybeam.moviedemo.interfaces.ItemClickInterface
import com.brainybeam.moviedemo.models.SearchData

/**
 * Created by BrainyBeam on 07-Jan-19.
 */
class SearchAdapter(private var listSearch: ArrayList<SearchData>, private var itemClickInterface: ItemClickInterface) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.raw_search_list, parent, false))
    }

    override fun getItemCount(): Int {
        return listSearch.size
    }

    fun refreshData(list: ArrayList<SearchData>){
        listSearch.clear()
        listSearch.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val searchData = listSearch[position]
        val binding: RawSearchListBinding = holder.getBinding()
        binding.searchData = searchData
        binding.executePendingBindings()
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private var binding: RawSearchListBinding = getBinding()
        init {
            binding.llRecentSearch.setOnClickListener { v ->
                itemClickInterface.onItemClick(v, adapterPosition, listSearch[adapterPosition])
            }
        }
    }
}