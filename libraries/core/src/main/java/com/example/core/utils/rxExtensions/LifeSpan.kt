package com.example.core.utils.rxExtensions


import androidx.fragment.app.FragmentActivity

/**
 * Defines how long the life would be alive.
 */
enum class LifeSpan {

    /**
     * This [LifeSpan] is just for [Life] objects who want to expand their life over multiple activities of a
     * user flow, which means onDestroy would not make them die. Check out [LifeStore.provideLife] function for
     * more details. Assuming the user flow includes multiple Activities.
     */
    USER_FLOW,

    /**
     * This [LifeSpan] is just for [Life] objects who want to expand their life over their owner activity, which
     * means configuration changes would not make them die. Check out [FragmentActivity.provideLife] extension
     * function for more details.
     */
    CONFIGURATION_CHANGED,

    /**
     * In case of object being [Life], it's alive after onCreate and before onDestroy of LifecycleOwner.
     * In case of object being [ELife], it's alive after onCreate and before onDestroy or onSaveInstanceState
     * of SavedStateRegistryOwner, depends which one is called sooner.
     */
    CREATED,

    /**
     * In case of object being [Life], it's alive after onStart and before onStop or onDestroy of LifecycleOwner,
     * depends which one is called sooner.
     * In case of object being [ELife], it's alive after onStart and before onStop or onDestroy or
     * onSaveInstanceState of SavedStateRegistryOwner, depends which one is called sooner.
     */
    STARTED,

    /**
     * In case of object being [Life], it's alive after onResume and before onPause or onStop or onDestroy of
     * LifecycleOwner, depends which one is called sooner.
     * In case of object being [ELife], it's alive after onResume and before onPause or onStop or onDestroy or
     * onSaveInstanceState of SavedStateRegistryOwner, depends which one is called sooner
     */
    RESUMED
}
