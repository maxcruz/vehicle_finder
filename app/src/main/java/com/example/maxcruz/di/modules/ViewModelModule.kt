package com.example.maxcruz.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.maxcruz.core.ViewModelFactory
import com.example.maxcruz.list.VehicleListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

/**
 * Provide the ViewModelFactory to get any ViewModel with his dependencies
 */
@Module
abstract class ViewModelModule {

    @Suppress("unused")
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Suppress("unused")
    @Binds
    @IntoMap
    @ViewModelKey(VehicleListViewModel::class)
    internal abstract fun listViewModel(viewModelVehicle: VehicleListViewModel): ViewModel

}