package com.example.core.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.reactivex.disposables.CompositeDisposable

open class BaseService : Service() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

}