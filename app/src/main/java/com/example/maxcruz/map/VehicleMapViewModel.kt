package com.example.maxcruz.map

import com.example.maxcruz.core.ObservableViewModel
import com.example.maxcruz.core.SingleLiveEvent
import com.example.maxcruz.domain.interactors.GetPoints
import com.example.maxcruz.domain.models.Point
import javax.inject.Inject

class VehicleMapViewModel @Inject constructor(private val getPoints: GetPoints)
    : ObservableViewModel() {

    val command: SingleLiveEvent<Command> = SingleLiveEvent()

    var point: Point? = null
        set(value) {
            if (value == null) {
                command.value = Command.EmptyParameter
            }
        }


    sealed class Command {

        object EmptyParameter: Command()

    }
}