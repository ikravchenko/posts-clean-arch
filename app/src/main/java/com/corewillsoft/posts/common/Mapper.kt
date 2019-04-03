package com.corewillsoft.posts.common

/**
 * Mapper interface to provide mapping.
 * You can use this interface to map between app layers
 *
 * @param <FROM> the class, which should be mapped from
 * @param <TO> the class, which is returned by the map
 *
 * @see BidirectionalMapper
 */
interface Mapper<in FROM, out TO> {

    /**
     * Executes the mapping.
     *
     * @param from the object which acts as the source
     * @result an instance of <TO>, which contains the mapped data based on @param from
     */
    fun map(from: FROM): TO
}