package com.example.maxcruz.data

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.example.maxcruz.data.exception.ServerException
import com.example.maxcruz.data.remote.MyTaxiService
import com.example.maxcruz.domain.models.Point
import com.example.maxcruz.domain.repository.PointListRepository

/**
 * Repository pattern to retrieve vehicle data
 */
class VehicleRepository(private val service: MyTaxiService): PointListRepository {

    /**
     * Get a list of vehicle points
     */
    override fun getPointList(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Either<Failure, List<Point>> {
        return try {
            val response = service.getVehicles(lat1, lon1, lat2, lon2).execute()
            when (response.isSuccessful) {
                true -> Right(response.body()?.poiList?.mapNotNull { it } ?: listOf())
                false -> Left(Failure(ServerException()))
            }
        } catch (exception: Throwable) {
            Left(Failure(exception))
        }
    }

}