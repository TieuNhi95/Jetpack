package com.example.core.utils.rxExtensions

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner

/**
 * The handler to keep all parts together and handle calling of [born] and [die] of [Life],
 * based on [LifeSpan] that they [register] by.
 */
interface AndroidLifeHandler {

    /**
     * Registers an instance of [life], based on the [lifeSpan], to handle calling of [Life.onBorn] and
     * [Life.onDie]. It'll unregister itself when onDestroy gets called.
     *
     * The [owner] is responsible to avoid GC to collect this object, by keeping a reference to it.
     */
    @MainThread
    fun register(owner: LifecycleOwner, life: Life, lifeSpan: LifeSpan)
}
