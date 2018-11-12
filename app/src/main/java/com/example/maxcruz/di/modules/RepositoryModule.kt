package com.example.maxcruz.di.modules

import com.example.maxcruz.data.VehicleRepository
import com.example.maxcruz.data.remote.MyTaxiService
import com.example.maxcruz.data.remote.createService
import com.example.maxcruz.domain.repository.PointListRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Data access module
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesPointListRepository(service: MyTaxiService): PointListRepository = VehicleRepository(service)

    @Provides
    @Singleton
    fun providesMyTaxiService(): MyTaxiService = createService(MyTaxiService::class.java, MyTaxiService.URL)

}