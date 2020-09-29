package com.example.core.utils.rxExtensions


import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

abstract class BaseLifeHandler : LifecycleObserver {

    protected abstract val lifecycle: Lifecycle
    protected abstract val lifeSpan: LifeSpan

    @MainThread
    protected abstract fun born()

    @MainThread
    protected abstract fun die()

    @MainThread
    protected abstract fun unregister()

    protected fun bornIfPossible() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) && lifeSpan == LifeSpan.RESUMED) {
            born()
        } else if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED) && lifeSpan == LifeSpan.STARTED) {
            born()
        } else if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED) && lifeSpan == LifeSpan.CREATED) {
            born()
        } else if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED) && lifeSpan == LifeSpan.CONFIGURATION_CHANGED) {
            born()
        } else if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED) && lifeSpan == LifeSpan.USER_FLOW) {
            born()
        }
    }

    private fun dieIfPossible() {
        if (lifeSpan == LifeSpan.RESUMED) {
            die()
        } else if ((lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED) ||
                    lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED)) &&
            lifeSpan == LifeSpan.STARTED
        ) {
            die()
        } else if (lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED) && lifeSpan == LifeSpan.CREATED) {
            die()
        } else if (lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED) && lifeSpan == LifeSpan.USER_FLOW) {
            die()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        bornIfPossible()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        bornIfPossible()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        bornIfPossible()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        dieIfPossible()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        dieIfPossible()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        dieIfPossible()
        unregister()
    }
}
