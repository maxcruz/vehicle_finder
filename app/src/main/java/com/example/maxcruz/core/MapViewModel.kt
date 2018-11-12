package com.example.maxcruz.core

import android.arch.lifecycle.ViewModel
import javax.inject.Provider

/**
 * Type alias to improve the readability of this big map
 */
typealias MapViewModel = Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>