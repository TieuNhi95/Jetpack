package com.example.core.model.network

import com.google.gson.annotations.SerializedName

open class BaseMetaResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)
