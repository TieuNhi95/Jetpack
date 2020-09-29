package com.example.core.utils.rxExtensions

import androidx.savedstate.SavedStateRegistryOwner

/**
 * The handler to keep all parts together and handle calling of [born] and [die] of [ELife],
 * based on [LifeSpan] that they [register] by.
 */
interface AndroidELifeHandler {

    /**
     * Registers an instance of [life], based on the [lifeSpan], to handle calling of [ELife.onBorn] and
     * [ELife.onDie]. It'll unregister itself when onDestroy gets called.
     *
     * The [owner] is responsible to avoid GC to collect this object, by keeping a reference to it.
     *
     * The [key] is used to save and restore to SavedStateRegistry. So it must be unique between all handlers of one
     * [owner].
     */
    fun register(owner: SavedStateRegistryOwner, life: ELife, lifeSpan: LifeSpan, key: String)
}
