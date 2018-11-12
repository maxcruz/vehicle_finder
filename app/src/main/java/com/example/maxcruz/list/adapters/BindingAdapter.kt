package com.example.maxcruz.list.adapters

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.example.maxcruz.domain.models.FleetType
import com.example.maxcruz.domain.models.Point

@BindingAdapter("fleetType")
fun TextView.setFleetType(type: FleetType?) {
    when (type) {
        is FleetType.Taxi -> this.text = "TAXI"
        is FleetType.Pooling -> this.text = "POOLING"
    }
}

@BindingAdapter("data")
fun RecyclerView.setVehicleList(items: List<Point>) {
    if (this.adapter is VehicleAdapter) {
        (this.adapter as VehicleAdapter).updateList(items)
    }
}