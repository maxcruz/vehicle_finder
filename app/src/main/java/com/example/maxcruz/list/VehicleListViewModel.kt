package com.example.maxcruz.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.maxcruz.core.ObservableViewModel
import com.example.maxcruz.core.SingleLiveEvent
import com.example.maxcruz.domain.interactors.GetAddress
import com.example.maxcruz.domain.interactors.GetPoints
import com.example.maxcruz.domain.models.Point
import javax.inject.Inject

class VehicleListViewModel @Inject constructor(private val getPoints: GetPoints, val getAddress: GetAddress)
    : ObservableViewModel() {

    val command: SingleLiveEvent<Command> = SingleLiveEvent()
    lateinit var pointList: MutableLiveData<List<Point>>
    var isRefreshing = false

    fun getVehiclePoints(): LiveData<List<Point>> {
        if (!::pointList.isInitialized) {
            pointList = MutableLiveData()
            loadPoints()
        }
        return pointList
    }

    fun loadPoints() {
        isRefreshing = true
        notifyChange()
        val square = GetPoints.Square(LAT1, LON1, LAT2, LON2)
        getPoints.execute(square) { either ->
            isRefreshing = false
            notifyChange()
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

        class Error(val message: String): Command()

    }

    companion object {
        const val LAT1 = 53.694865
        const val LON1 = 9.757589
        const val LAT2 = 53.394655
        const val LON2 = 10.099891
    }

}