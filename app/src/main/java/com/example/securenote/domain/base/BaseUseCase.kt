package com.example.securenote.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    // For use cases that return Flow
    abstract suspend fun execute(parameters: P): Flow<Result<R>>

    // Helper function to handle flow execution with error handling
    protected fun Flow<Result<R>>.handleFlow(): Flow<Result<R>> {
        return this
            .catch { e -> emit(Result.failure(e)) }
            .flowOn(coroutineDispatcher)
    }

    // For use cases that return direct result
    protected suspend fun <T> executeBlocking(block: suspend () -> T): Result<T> {
        return try {
            withContext(coroutineDispatcher) {
                Result.success(block())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}