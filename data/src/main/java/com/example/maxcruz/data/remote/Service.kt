package com.example.maxcruz.data.remote

import com.example.maxcruz.data.remote.adapters.FleetTypeAdapter
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Get the remote API service client
 *
 * @param clazz HTTP API interface defined with Retrofit
 * @param endPoint Base URL endpoint
 */
fun <T> createService(clazz: Class<T>, endPoint: String): T = Retrofit.Builder()
    .baseUrl(endPoint)
    .addConverterFactory(getConverterFactory())
    .build()
    .create(clazz)

fun getConverterFactory(): MoshiConverterFactory {
    val parser = Moshi.Builder().add(FleetTypeAdapter()).build()
    return MoshiConverterFactory.create(parser)
}