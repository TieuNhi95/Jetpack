package com.example.core.bus.event

import com.example.core.bus.BaseEvent
import com.example.core.bus.action.BackAction

class BackEvent : BaseEvent<BackAction>() {

    private object Holder {
        val INSTANCE = BackEvent()
    }

    companion object {
        @JvmStatic
        fun getInstance(): BackEvent {
            return Holder.INSTANCE
        }
    }
}