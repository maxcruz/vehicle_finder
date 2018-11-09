package com.example.maxcruz.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import com.example.maxcruz.domain.models.Point
import com.example.maxcruz.domain.repository.PointListRepository

class GetPoints(val repository: PointListRepository): UseCase<List<Point>>() {

    override suspend fun run(values: Any?): Either<Failure, List<Point>> {
        TODO("not implemented")
    }

}