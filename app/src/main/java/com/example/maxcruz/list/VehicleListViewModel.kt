package com.example.maxcruz.list

import android.arch.lifecycle.ViewModel
import com.example.maxcruz.domain.interactors.GetPoints
import com.example.maxcruz.domain.models.Point
import javax.inject.Inject

class VehicleListViewModel @Inject constructor(private val getPoints: GetPoints): ViewModel() {

    var pointList = listOf<Point>()

}