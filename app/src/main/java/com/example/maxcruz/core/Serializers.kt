package com.example.maxcruz.core

import com.example.maxcruz.data.remote.adapters.FleetTypeAdapter
import com.example.maxcruz.domain.models.Point
import com.squareup.moshi.Moshi

fun Point.toJson(): String {
    val parser = Moshi.Builder().add(FleetTypeAdapter()).build().adapter(Point::class.java)
    return parser.toJson(this)
}

fun <T> String?.fromJson(type: Class<T>): T? {
    if (this.isNullOrBlank()) return null
    val parser = Moshi.Builder().add(FleetTypeAdapter()).build().adapter(type)
    return parser.fromJson(this)
}