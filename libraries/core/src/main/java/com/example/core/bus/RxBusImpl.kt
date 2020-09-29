package com.example.core.bus

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

@Singleton
class RxBusImpl : RxBus {

    private var sPublishSubject: PublishSubject<BaseRxEvent> = PublishSubject.create()

    override fun publish(event: BaseRxEvent) {
        sPublishSubject.onNext(event)
    }

    override fun subscribe(sub: Consumer<BaseRxEvent>): Disposable {
        return sPublishSubject.subscribe(sub)
    }

}