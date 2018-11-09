package com.example.maxcruz.domain.models

/**
 * Vehicle position mapping
 */

data class Point(
    val coordinate: Coordinate? = null,
    val fleetType: FleetType? = null,
    val heading: Double? = null,
    val id: Int? = null
)
