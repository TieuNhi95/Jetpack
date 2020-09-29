package com.example.core.bus.event

import com.example.core.bus.BaseRxEvent

class NetworkEvent(code: Int) : BaseRxEvent() {
    val requestCode = code

}