package com.example.core.utils.rxExtensions

import androidx.annotation.MainThread

/***
 * Defines an object with simply two callbacks [onBorn] and [onDie] to complete the lifecycle. The time after
 * calling [onBorn] and before [onDie] is when the object is alive.
 *
 * [AndroidLifeHandler] is able to register this object.
 */
interface Life {

    /**
     * Calls when the life starts, depending on the [LifeSpan] while registering. This method will be called
     * onCreate, onStart or onResume of the LifecycleOwner.
     */
    @MainThread
    fun onBorn()

    /**
     * Calls when the life ends, depending on the [LifeSpan] while registering. This method will be called
     * onPause, onStop or onDestroy of the LifecycleOwner.
     */
    @MainThread
    fun onDie()
}
