package com.example.maxcruz.list.adapters

import android.databinding.BindingAdapter
import android.location.Geocoder
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.example.maxcruz.domain.interactors.GetAddress
import com.example.maxcruz.domain.models.Coordinate
import com.example.maxcruz.domain.models.FleetType
import com.example.maxcruz.domain.models.Point
import com.example.maxcruz.vehiclefinder.R
import java.util.*

@BindingAdapter("fleetType")
fun TextView.setFleetType(type: FleetType?) {
    when (type) {
        is FleetType.Taxi -> {
            this.text = context.getString(R.string.taxi_tag)
            this.setTextColor(ContextCompat.getColor(context, R.color.colorYellow))
        }
        is FleetType.Pooling -> {
            this.text = context.getString(R.string.pooling_tag)
            this.setTextColor(ContextCompat.getColor(context, R.color.colorIndigo))
        }
    }
}

@BindingAdapter("fleetType")
fun ImageView.setFleetType(type: FleetType?) {
    when (type) {
        is FleetType.Taxi -> {
            this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_local_taxi))
        }
        is FleetType.Pooling -> {
            this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_car_pooling))
        }
    }
}

@BindingAdapter("address", "use_case")
fun TextView.setAddress(coordinate: Coordinate?, useCase: GetAddress) {
    val parameter = GetAddress.CallGeocoder(coordinate) { latitude, longitude ->
        val geocoder = Geocoder(this.context, Locale.GERMANY)
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        addresses.firstOrNull()?.getAddressLine(0) ?: "Unknown"
    }
    useCase.execute(parameter) { either ->
        either.fold(
            {
                this.text = it.exception.message
            },
            {
                this.text = it
            })
    }
}

@BindingAdapter("data")
fun RecyclerView.setVehicleList(items: List<Point>) {
    if (this.adapter is VehicleAdapter) {
        (this.adapter as VehicleAdapter).updateList(items)
    }
}

@BindingAdapter("android:text")
fun TextView.setText(id: Int?) {
    val text = id?.toString() ?: "?"
    this.text = text
}