package com.example.core.bus

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

interface RxBus {
    fun publish(event: BaseRxEvent)
    fun subscribe(sub: Consumer<BaseRxEvent>): Disposable
}