package com.corewillsoft.posts.common

/**
 * Mapper interface to provide bidirectional mapping between models
 *
 * @see Mapper
 */
interface BidirectionalMapper<L, R> {

    fun from(from: L): R

    fun to(from: R): L
}