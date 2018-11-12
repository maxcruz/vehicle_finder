package com.example.maxcruz

import android.app.Application
import com.example.maxcruz.di.components.DaggerVehicleListComponent
import com.example.maxcruz.di.components.VehicleListComponent
import com.example.maxcruz.di.modules.GetPointsModule
import com.example.maxcruz.di.modules.RepositoryModule

/**
 * Application class for DI
 */
class VehicleFinderApplication: Application() {

    fun getListComponent(): VehicleListComponent {
        return DaggerVehicleListComponent.builder()
            .getPointsModule(GetPointsModule())
            .repositoryModule(RepositoryModule())
            .build()
    }

}