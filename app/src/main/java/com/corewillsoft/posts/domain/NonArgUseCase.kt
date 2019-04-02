package com.corewillsoft.posts.domain

/**
 * A UseCase with no parameter arguments
 * @param <R> return type which will be returned by the UseCase
 *
 * @see UseCase
 */
interface NonArgUseCase<out R> {
    /**
     * Executes the use case.
     * @return <R> - instance of result.
     */
    fun execute(): R
}