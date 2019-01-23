package com.example.maxcruz.data

import android.support.test.InstrumentationRegistry
import com.example.maxcruz.data.remote.MyPositionsService
import com.example.maxcruz.data.remote.createService
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyDouble


class VehicleRepositoryIntegrationTest {

    @get:Rule
    var mockWebServerRule = MockWebServerRule()

    private lateinit var service: MyPositionsService

    @Before
    fun setUp() {
        service = createService(MyPositionsService::class.java, mockWebServerRule.url)
    }

    @Test
    fun getExchangeRateSuccess() {
        // Given
        mockWebServerRule.enqueueResponse(getBodyFromFile())

        // When
        val response = service.getVehicles(anyDouble(), anyDouble(), anyDouble(), anyDouble()).execute()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()?.poiList?.isNotEmpty() ?: false).isTrue()
    }

    @Test
    fun getExchangeRateError() {
        // Given
        mockWebServerRule.enqueueNotFound()

        // When
        val response = service.getVehicles(anyDouble(), anyDouble(), anyDouble(), anyDouble()).execute()

        // Then
        assertThat(response.isSuccessful).isFalse()
        assertThat(response.errorBody()).isNotNull()
    }

    private fun getBodyFromFile(): String {
        val context = InstrumentationRegistry.getInstrumentation().context
        return RestServiceTestHelper.getStringFromFile(context, "points_success.json")
    }

}