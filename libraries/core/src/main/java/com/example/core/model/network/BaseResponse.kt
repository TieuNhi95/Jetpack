package com.example.core.model.network

import com.google.gson.annotations.SerializedName

open class BaseResponse<M : BaseMetaResponse, D : BaseDataResponse>(
    @SerializedName("meta")
    var metaResponse: M?,
    @SerializedName("data")
    var dataResponse: D?
)
