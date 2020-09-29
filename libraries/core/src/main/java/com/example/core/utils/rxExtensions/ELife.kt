package com.example.core.utils.rxExtensions

import android.os.Bundle
import androidx.annotation.MainThread

/***
 * Defines an object with **extended** lifecycle respect to [Life]. It has simply two callbacks [onBorn] and [onDie] to
 * complete the lifecycle. The time after calling [onBorn] and before [onDie] is when the object is alive.
 *
 * [AndroidELifeHandler] is able to register this object.
 */
interface ELife {

    /**
     * Calls when the life starts, depending on the [LifeSpan] while registering. This method will be called
     * onCreate, onStart or onResume of the SavedStateRegistryOwner.
     *
     * The [bundle] would be null in case of first born. After first die, [bundle] must be from the previous dead
     * instance.
     */
    @MainThread
    fun onBorn(bundle: Bundle?)

    /**
     * Calls when the life ends, depending on the [LifeSpan] while registering. This method will be called
     * onPause, onStop, onDestroy or onSaveInstanceState of the SavedStateRegistryOwner.
     *
     * If you need to set the returning bundle to be null, rethink please, because it's probable that you need an
     * instance of [Life] instead of [ELife]!
     */
    @MainThread
    fun onDie(): Bundle
}
