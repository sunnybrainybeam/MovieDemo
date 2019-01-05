package com.brainybeam.moviedemo.baseStructures

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.brainybeam.moviedemo.R

/**
 * Created by BrainyBeam on 04-Jan-19.
 */
open class BaseAppCompactActivity : AppCompatActivity() {

    private val TAG = BaseAppCompactActivity::class.java.simpleName
    private lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
    }

    fun setLayoutView(layoutId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    protected fun <T : ViewDataBinding> getViewBinding(): T {
        return binding as T
    }

    protected fun setToolbar(toolbar: Toolbar, title: String, isBackArrow: Boolean) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        if (isBackArrow) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }

    }


}