package com.example.maxcruz.di.components

import com.example.maxcruz.di.modules.UseCaseModule
import com.example.maxcruz.di.modules.RepositoryModule
import com.example.maxcruz.di.modules.ViewModelModule
import com.example.maxcruz.list.VehicleListFragment
import com.example.maxcruz.map.VehicleMapFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, UseCaseModule::class, RepositoryModule::class])
interface VehicleListComponent {

    fun inject(fragment: VehicleListFragment)

    fun inject(fragment: VehicleMapFragment)

}