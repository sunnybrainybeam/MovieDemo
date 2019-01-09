package com.brainybeam.moviedemo.baseStructures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by BrainyBeam on 05-Jan-19.
 * @author BrainyBeam
 *
 * All fragments used in this project are derived from this BaseFragment class.
 * This class is for generalization of fragments.
 */
open class BaseFragment : Fragment() {

    private var layoutId: Int = 0
    private lateinit var binding: ViewDataBinding

    protected fun setView(layoutId: Int) {
        this.layoutId = layoutId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    protected fun <T : ViewDataBinding> getBinding(): T {
        return binding as T
    }
}