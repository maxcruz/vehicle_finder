package com.example.maxcruz.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.maxcruz.core.ViewModelFactory
import com.example.maxcruz.list.VehicleListViewModel
import com.example.maxcruz.map.VehicleMapViewModel
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
@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(VehicleListViewModel::class)
    internal abstract fun listViewModel(viewModelList: VehicleListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VehicleMapViewModel::class)
    internal abstract fun mapViewModel(viewModelMap: VehicleMapViewModel): ViewModel

}