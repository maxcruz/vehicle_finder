package com.example.maxcruz.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import com.example.maxcruz.domain.models.Point
import com.example.maxcruz.domain.repository.PointListRepository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


/**
 * Use case to retrieve points
 */
class GetPoints(private val repository: PointListRepository,
                background: CoroutineContext = Dispatchers.IO,
                foreground: CoroutineContext = Dispatchers.Main):
    UseCase<List<Point>, GetPoints.Square>(background, foreground) {

    override suspend fun run(parameter: Square?): Either<Failure, List<Point>> {
        if (parameter == null) return Left(Failure(WrongParameters()))
        return repository.getPointList(parameter.lat1, parameter.lon1, parameter.lat2, parameter.lon2)
    }

    /**
     * Use case parameters
     */
    data class Square(val lat1: Double, val lon1: Double, val lat2: Double, val lon2: Double): UseCase.Input

    /**
     * App level exception for empty parameters
     */
    class WrongParameters: Throwable("Wrong parameters")

}