package com.wanblog.base

import androidx.fragment.app.Fragment

abstract class BaseLazyFragment : Fragment() {

    private var isLoaded = false

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    abstract fun lazyInit()

}