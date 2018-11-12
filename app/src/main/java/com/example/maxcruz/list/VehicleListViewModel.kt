package com.example.maxcruz.list

import com.example.maxcruz.core.ObservableViewModel
import com.example.maxcruz.core.SingleLiveEvent
import com.example.maxcruz.domain.interactors.GetAddress
import com.example.maxcruz.domain.interactors.GetPoints
import com.example.maxcruz.domain.models.Coordinate
import com.example.maxcruz.domain.models.Point
import javax.inject.Inject

class VehicleListViewModel @Inject constructor(private val getPoints: GetPoints,
                                               val getAddress: GetAddress) : ObservableViewModel() {

    var pointList = listOf<Point>()
    var isRefreshing = false
    val command: SingleLiveEvent<Command> = SingleLiveEvent()

    fun loadPoints() {
        isRefreshing = true
        notifyChange()
        val square = GetPoints.Square(LAT1, LON1, LAT2, LON2)
        getPoints.execute(square) { either ->
            isRefreshing = false
            notifyChange()
            either.fold(
                { failure ->
                    val message = failure.exception.message ?: "Something went wrong :-("
                    command.value = Command.Error(message)
                },
                { list ->
                    pointList = list
                    notifyChange()
                })
        }
    }

    sealed class Command {

        class Error(val message: String): Command()

    }

    companion object {
        const val LAT1 = 53.694865
        const val LON1 = 9.757589
        const val LAT2 = 53.394655
        const val LON2 = 10.099891
    }

}