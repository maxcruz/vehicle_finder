package com.example.maxcruz.data.remote.adapters

import com.example.maxcruz.domain.models.FleetType
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson


class FleetTypeAdapter {

    @ToJson
    fun toJson(fleetType: FleetType): String {
        return when (fleetType) {
            FleetType.Taxi -> "TAXI"
            FleetType.Pooling -> "POOLING"
        }
    }

    @FromJson
    fun fromJson(fleetType: String): FleetType {
        return when (fleetType) {
            "POOLING" -> FleetType.Pooling
            "TAXI" -> FleetType.Taxi
            else -> throw JsonDataException("Unknown type: $fleetType")
        }
    }

}