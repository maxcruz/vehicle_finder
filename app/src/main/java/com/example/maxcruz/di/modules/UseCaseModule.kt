package com.example.maxcruz.di.modules

import com.example.maxcruz.domain.interactors.GetAddress
import com.example.maxcruz.domain.interactors.GetPoints
import com.example.maxcruz.domain.repository.PointListRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dependencies tree used by the list screen
 */
@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun providesGetPoints(repository: PointListRepository): GetPoints = GetPoints(repository)

    @Provides
    @Singleton
    fun providesGetAddress(): GetAddress = GetAddress()

}