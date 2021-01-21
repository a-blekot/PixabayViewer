package com.example.pixabayviewer.view.ui

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.pixabayviewer.MainActivity
import dagger.android.support.AndroidSupportInjection

open class BaseFragment constructor(
    @LayoutRes
    resId: Int,
    @StringRes
    private val title: Int
) : Fragment(resId) {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? MainActivity)?.run {
            supportActionBar?.title = getString(this@BaseFragment.title)
        }
    }
}