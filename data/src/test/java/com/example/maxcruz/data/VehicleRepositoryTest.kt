package com.example.maxcruz.data

import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.example.maxcruz.data.dto.Point
import com.example.maxcruz.data.dto.PointList
import com.example.maxcruz.data.exception.ServerException
import com.example.maxcruz.data.remote.MyTaxiService
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.anyDouble
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response

class VehicleRepositoryTest {

    private lateinit var repository: VehicleRepository

    @Mock
    private lateinit var response: Response<PointList>
    @Mock
    private lateinit var call: Call<PointList>
    @Mock
    private lateinit var service: MyTaxiService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = VehicleRepository(service)
    }

    @Test
    fun getPointListSuccess() {
        // Given
        given(response.isSuccessful).willReturn(true)
        given(response.body()).willReturn(PointList(listOf()))
        given(call.execute()).willReturn(response)
        given(service.getVehicles(anyDouble(), anyDouble(), anyDouble(), anyDouble())).willReturn(call)

        // When
        val result = repository.getPointList(anyDouble(), anyDouble(), anyDouble(), anyDouble())

        // Then
        assertThat(result.isRight()).isTrue()
        assertThat(result).isEqualTo(Right(listOf<Point>()))
    }

    @Test
    fun getPointListFailure() {
        // Given
        val throwable = ServerException()
        given(call.execute()).willThrow(throwable)
        given(service.getVehicles(anyDouble(), anyDouble(), anyDouble(), anyDouble())).willReturn(call)

        // When
        val result = repository.getPointList(anyDouble(), anyDouble(), anyDouble(), anyDouble())

        // Then
        assertThat(result.isLeft()).isTrue()
        assertThat(result).isEqualTo(Left(Failure(throwable)))
    }

}