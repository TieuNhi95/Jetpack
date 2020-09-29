package com.example.core.utils.rxExtensions


import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * The implementation of [AndroidLifeHandler].
 */
class AndroidLifeHandlerImpl : BaseLifeHandler(), AndroidLifeHandler {

    override val lifecycle by lazy { owner.lifecycle }
    private var alive: Boolean = false

    override lateinit var lifeSpan: LifeSpan
    private lateinit var owner: LifecycleOwner
    private lateinit var life: Life

    @MainThread
    override fun register(owner: LifecycleOwner, life: Life, lifeSpan: LifeSpan) {
        this.owner = owner
        this.life = life
        this.lifeSpan = lifeSpan
        registerIfPossible()
    }

    private fun registerIfPossible() {
        if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
            lifecycle.addObserver(this)
            bornIfPossible()
        }
    }

    @MainThread
    override fun born() {
        if (!alive) {
            alive = true
            life.onBorn()
        }
    }

    @MainThread
    override fun die() {
        if (alive) {
            alive = false
            life.onDie()
        }
    }

    @MainThread
    override fun unregister() {
        lifecycle.removeObserver(this)
    }
}
