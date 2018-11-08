package com.example.maxcruz.data.remote

import com.example.maxcruz.data.dto.PointList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Fake vehicles API
 */
interface MyTaxiService {

    companion object {

        const val URL = "https://fake-poi-api.mytaxi.com"

    }

    @GET("/")
    fun getVehicles(
        @Query("p1Lat") lat1: Double,
        @Query("p1Lon") lon1: Double,
        @Query("p2Lat") lat2: Double,
        @Query("p2Lon") lon2: Double
    ): Call<PointList>

}