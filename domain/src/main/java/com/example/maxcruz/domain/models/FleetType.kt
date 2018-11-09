package com.example.maxcruz.domain.models

/**
 * Fleet type data structure
 */
sealed class FleetType {
    object Taxi: FleetType()
    object Pooling: FleetType()
}