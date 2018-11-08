package com.example.maxcruz.data.dto

/**
 * Vehicle position mapping
 */

data class Point(
	val coordinate: Coordinate? = null,
	val fleetType: String? = null,
	val heading: Double? = null,
	val id: Int? = null
)
