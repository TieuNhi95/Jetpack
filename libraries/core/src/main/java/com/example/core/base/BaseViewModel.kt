package com.example.core.base

import androidx.lifecycle.ViewModel
import com.example.core.model.network.BaseDataResponse
import com.example.core.model.network.BaseMetaResponse
import com.example.core.model.network.BaseResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException

abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun <T : BaseDataResponse> handleError(
        throwable: Throwable,
        callBack: (result: BaseResponse<BaseMetaResponse, T>) -> Unit
    ) {
        if (throwable is ConnectException) {
//            messageError.postValue(throwable.message)
        } else if (throwable is HttpException) {
            var errorBody: String? = null
            try {
                errorBody = throwable.response()!!.errorBody()!!.string()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val typeResponse =
                object : TypeToken<BaseResponse<BaseMetaResponse, BaseDataResponse>>() {}.type
            var response: BaseResponse<BaseMetaResponse, T>? = null
            try {
                response =
                    Gson().fromJson(
                        errorBody,
                        typeResponse
                    ) as BaseResponse<BaseMetaResponse, T>?
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (response != null) {
                Timber.tag("handleError").d(response.toString())
//                messageError.postValue(response.metaResponse?.message)
                callBack.invoke(response)
            } else {
//                messageError.postValue(throwable.message)
            }
        } else {
//            messageError.postValue(R.string.not_connected_internet)
        }

    }

}