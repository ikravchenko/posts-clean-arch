package com.corewillsoft.posts.domain

/**
 * Mapper interface to provide bidirectional mapping between models
 *
 * @see Mapper
 */
interface BidirectionalMapper<L, R> {

    fun from(from: L): R

    fun to(from: R): L
}