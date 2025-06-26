package com.example.securenote.domain.usecase

abstract class UseCase<in P, R> {
    abstract suspend operator fun invoke(params: P): Result<R>

    open class NoParams
}