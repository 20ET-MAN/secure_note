package com.example.securenote.domain.usecase

abstract class BaseUseCase<in P, R> {
    abstract suspend operator fun invoke(params: P): R

    open class NoParams
}