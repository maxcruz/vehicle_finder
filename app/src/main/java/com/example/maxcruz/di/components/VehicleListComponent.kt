package com.example.maxcruz.di.components

import com.example.maxcruz.di.modules.GetPointsModule
import com.example.maxcruz.di.modules.RepositoryModule
import com.example.maxcruz.di.modules.ViewModelModule
import com.example.maxcruz.list.VehicleListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, GetPointsModule::class, RepositoryModule::class])
interface VehicleListComponent {

    fun inject(fragment: VehicleListFragment)

}