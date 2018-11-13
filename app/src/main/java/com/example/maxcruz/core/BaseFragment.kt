package com.example.maxcruz.core

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.fragment_list.*

abstract class BaseFragment: Fragment() {

    override fun onResume() {
        super.onResume()
        activity?.title = getTitle()
    }

    protected fun showError(message: String) {
        activity?.let {
            Snackbar.make(layoutContainer, message, Snackbar.LENGTH_LONG).show()
        }
    }

    protected abstract fun getTitle(): String

}