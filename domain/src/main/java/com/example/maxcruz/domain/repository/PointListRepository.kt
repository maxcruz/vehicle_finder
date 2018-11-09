package com.example.maxcruz.domain.repository

import arrow.core.Either
import arrow.core.Failure
import com.example.maxcruz.domain.models.Point

/**
 * Interface to decouple data access dependency
 */
interface PointListRepository {

    fun getPointList(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Either<Failure, List<Point>>

}