package com.corewillsoft.posts.presenter

/**
 * Generic lifecycle to bind presenter to external framework lifecycle
 */
interface PresenterLifecycle {

    fun start()

    fun stop()
}