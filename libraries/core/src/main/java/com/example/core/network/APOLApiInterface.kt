package com.example.core.network

import com.example.core.model.network.BaseDataResponse
import com.example.core.model.network.BaseMetaResponse
import com.example.core.model.network.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APOLApiInterface {

    @POST("regist")
    @FormUrlEncoded
    fun registerToken(
        @Field("device_id") deviceId: String,
        @Field("notify_token") notifyToken: String,
        @Field("voip_token") voipToken: String,
        @Field("user_id") userId: String,
        @Field("device_type") deviceType: String,
        @Field("application_version") appVersion: String,
        @Field("sid") sid: String,
        @Field("site") site: String,
        @Field("app_type") appType: String
    ): Observable<BaseResponse<BaseMetaResponse, BaseDataResponse>>

    @POST("upd_noti_token")
    @FormUrlEncoded
    fun updateNotificationToken(
        @Field("user_id") userId: String,
        @Field("application_version") appVersion: String,
        @Field("notify_token") notifyToken: String,
        @Field("os_version") osVersion: String
    ): Observable<BaseResponse<BaseMetaResponse, BaseDataResponse>>

    @POST("upd_voip_token")
    @FormUrlEncoded
    fun updateVoipToken(
        @Field("user_id") userId: String,
        @Field("application_version") appVersion: String,
        @Field("notify_token") notifyToken: String,
        @Field("os_version") osVersion: String
    ): Observable<BaseResponse<BaseMetaResponse, BaseDataResponse>>
}