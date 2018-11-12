package com.example.maxcruz.core

import android.support.v4.app.Fragment

abstract class BaseFragment: Fragment() {

    override fun onResume() {
        super.onResume()
        activity?.title = getTitle()
    }

    protected abstract fun getTitle(): String

}