package com.example.core.utils.rxExtensions


import android.os.Bundle
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner

/**
 * The implementation of [AndroidELifeHandler].
 */
class AndroidELifeHandlerImpl : BaseLifeHandler(), SavedStateRegistry.SavedStateProvider,
    AndroidELifeHandler {

    override val lifecycle by lazy { owner.lifecycle }
    private val savedStateRegistry by lazy { owner.savedStateRegistry }
    private var alive: Boolean = false
    private var keepBundle: Bundle? = null

    override lateinit var lifeSpan: LifeSpan
    private lateinit var owner: SavedStateRegistryOwner
    private lateinit var life: ELife
    private lateinit var key: String

    @MainThread
    override fun register(
        owner: SavedStateRegistryOwner,
        life: ELife,
        lifeSpan: LifeSpan,
        key: String
    ) {
        this.owner = owner
        this.life = life
        this.lifeSpan = lifeSpan
        this.key = key
        registerIfPossible()
    }

    private fun registerIfPossible() {
        if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
            lifecycle.addObserver(this)
            savedStateRegistry.registerSavedStateProvider(key, this)
            bornIfPossible()
        }
    }

    @MainThread
    override fun born() {
        if (!alive) {
            alive = true
            life.onBorn(savedStateRegistry.consumeRestoredStateForKey(key) ?: keepBundle)
        }
    }

    @MainThread
    override fun die() {
        if (alive) {
            alive = false
            keepBundle = life.onDie()
        }
    }

    @MainThread
    override fun unregister() {
        lifecycle.removeObserver(this)
        savedStateRegistry.unregisterSavedStateProvider(key)
    }

    override fun saveState(): Bundle = if (alive) {
        alive = false
        life.onDie().apply { keepBundle = this }
    } else {
        keepBundle ?: Bundle()
    }
}
