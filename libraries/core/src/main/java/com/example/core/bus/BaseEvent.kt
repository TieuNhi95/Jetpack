package com.example.core.bus

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

/**
 * A : Action
 */
open class BaseEvent<A> {
    private val sPublishSubject: PublishSubject<A>? = PublishSubject.create()

    fun publish(action: A) {
        sPublishSubject!!.onNext(action)
    }

    fun subscribe(sub: Consumer<A>): Disposable {
        return sPublishSubject!!.subscribe(sub)
    }

}