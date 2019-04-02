package com.corewillsoft.posts.domain

/**
 * A UseCase executes a business case.
 * @param <P> parameter class which is consumed by the UseCase
 * @param <R> return type which will be returned by the UseCase
 */
interface UseCase<in P, out R> {

    /**
     * Executes the use case.
     * @param param <P> optional param for this use case execution.
     * @return <R> the type of result.
     */
    fun execute(param: P): R
}