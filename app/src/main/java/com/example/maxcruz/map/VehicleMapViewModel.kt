package com.example.maxcruz.map

import android.arch.lifecycle.MutableLiveData
import com.example.maxcruz.core.ObservableViewModel
import com.example.maxcruz.core.SingleLiveEvent
import com.example.maxcruz.domain.interactors.GetPoints
import com.example.maxcruz.domain.models.FleetType
import com.example.maxcruz.domain.models.Point
import javax.inject.Inject

class VehicleMapViewModel @Inject constructor(private val getPoints: GetPoints)
    : ObservableViewModel() {

    val command: SingleLiveEvent<Command> = SingleLiveEvent()
    val pointList = MutableLiveData<List<Point>>()

    var point: Point? = null
        set(value) {
            if (value == null) {
                command.value = Command.EmptyParameter
                return
            }
            loadMarker(value)
        }

    private fun loadMarker(point: Point?) {
        point?.let { (coordinate, fleetType) ->
            coordinate?.let { (latitude, longitude) ->
                if (latitude != null && longitude != null && fleetType != null) {
                    command.value = Command.LoadPoint(latitude, longitude, fleetType)
                }
            }
        }
    }

    fun fetchNeighbors(latitude1: Double, longitude1: Double, latitude2: Double, longitude2: Double) {
        val square = GetPoints.Square(latitude1, longitude1, latitude2, longitude2)
        getPoints.execute(square) { either ->
            either.fold(
                { failure ->
                    val message = failure.exception.message?.capitalize() ?: "Something went wrong :-("
                    command.value = Command.Error(message)
                },
                { list ->
                    pointList.postValue(list)
                })
        }
    }

    sealed class Command {

        object EmptyParameter: Command()
        class LoadPoint(val latitude: Double, val longitude: Double, val fleetType: FleetType): Command()
        class Error(val message: String): Command()

    }
}