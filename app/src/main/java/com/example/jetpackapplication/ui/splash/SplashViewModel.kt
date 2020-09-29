package com.example.jetpackapplication.ui.splash

import android.os.Handler
import androidx.hilt.lifecycle.ViewModelInject
import com.example.core.base.BaseViewModel
import com.example.core.utils.SingleLiveEvent

const val DURATION_SPLASH = 1500L

class SplashViewModel @ViewModelInject constructor() : BaseViewModel() {

    val actionSPlash = SingleLiveEvent<SplashActionState>()

    private val handlerSplash = Handler()
    private val runnableFinishSplash = {
        actionSPlash.value = SplashActionState.Finish
    }

    init {
        handlerSplash.postDelayed(runnableFinishSplash, DURATION_SPLASH)
    }

    override fun onCleared() {
        handlerSplash.removeCallbacks(runnableFinishSplash)
        super.onCleared()
    }
}

sealed class SplashActionState {
    object Finish : SplashActionState()
}