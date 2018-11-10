package com.example.maxcruz.domain.interactors

import arrow.core.Either
import arrow.core.Right
import com.example.maxcruz.domain.models.Point
import com.example.maxcruz.domain.repository.PointListRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class GetPointsTest {

    private lateinit var useCase: GetPoints

    @Mock
    lateinit var repository: PointListRepository

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = GetPoints(repository, Unconfined, Unconfined)
    }

    @Test
    fun getPointsSuccess() {
        runBlocking {
            // Given
            given(repository.getPointList(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .willReturn(Either.right(listOf()))
            val parameter = GetPoints.Square(anyDouble(), anyDouble(), anyDouble(), anyDouble())

            // When
            useCase.execute(parameter) {

                // Then
                assertThat(it.isRight()).isTrue()
                assertThat(it).isEqualTo(Right(listOf<Point>()))
            }

            verify(repository, times(1))
                .getPointList(anyDouble(), anyDouble(), anyDouble(), anyDouble())
        }
    }

    @Test
    fun getPointWithoutParameters() {
        runBlocking {
            // Given
            given(repository.getPointList(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .willReturn(Either.right(listOf()))

            // When
            useCase.execute {

                // Then
                assertThat(it.isLeft()).isTrue()
                it.mapLeft {failure ->
                    assertThat(failure.exception).isInstanceOf(GetPoints.WrongParameters::class.java)
                }
            }

            verify(repository, never()).getPointList(anyDouble(), anyDouble(), anyDouble(), anyDouble())
        }
    }

}