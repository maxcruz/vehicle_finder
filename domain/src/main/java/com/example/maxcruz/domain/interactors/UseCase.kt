package com.example.maxcruz.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


/**
 * Abstract execution unit for different use cases.
 *
 * @param <O> request type
 * @param <I> response parameter
 */
abstract class UseCase<T> {

    private val background: CoroutineContext = IO
    private val foreground: CoroutineContext = Main

    /**
     * Method to execute the use case
     */
    fun execute(parameter: Any? = null, block: (Either<Failure, T>) -> Unit) {
        GlobalScope.launch(foreground) {
            val job = async(background) {
                run(parameter)
            }
            block(job.await())
        }
    }

    /**
     * Abstract method to implement the use case
     */
    protected abstract suspend fun run(values: Any?): Either<Failure, T>

}
