package com.example.maxcruz.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.example.maxcruz.domain.models.Coordinate
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Use case to get the address deferring the computation of the geocoder
 */
class GetAddress(background: CoroutineContext = Dispatchers.IO,
                 foreground: CoroutineContext = Dispatchers.Main):
    UseCase<String, GetAddress.CallGeocoder>(background, foreground) {

    override suspend fun run(parameter: CallGeocoder?): Either<Failure, String> {
        if (parameter?.coordinate != null) {
            val (latitude, longitude) = parameter.coordinate
            if (latitude != null && longitude != null) {
                parameter.geocoder(latitude, longitude)?.let {
                    return Right(it)
                }
            }
        }
        return Left(Failure(Throwable("Unknown")))
    }


    class CallGeocoder(val coordinate: Coordinate?,
                       val geocoder: (latitude: Double, longitude: Double) -> String?): Input
}