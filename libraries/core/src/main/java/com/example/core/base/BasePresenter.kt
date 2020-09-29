package com.example.core.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter {

    val compositeDisposable = CompositeDisposable()

    fun onDestroy(){
        compositeDisposable.dispose()
    }
}