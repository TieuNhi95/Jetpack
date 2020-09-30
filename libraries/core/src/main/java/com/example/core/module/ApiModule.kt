package com.example.core.module

import android.content.Context
import android.net.ConnectivityManager
import com.example.core.network.ApiInterface
import com.example.core.network.NetworkInterceptor
import com.example.core.network.connectivity.ConnectivityChecker
import com.example.core.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Provides
    internal fun providesNetworkResolver(applicationContext: Context): ConnectivityChecker {
        return object : ConnectivityChecker {
            override fun isConnected(): Boolean {
                val connectivityManager =
                    applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                return activeNetworkInfo != null && activeNetworkInfo.isConnected
            }
        }
    }

    @Provides
    @Singleton
    fun provideParseJson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideApiInterface(parseJson: Gson, client: OkHttpClient)
            : ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.ApiComponents.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(parseJson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(cache: Cache?): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                    .newBuilder()
//                    .addHeader(
//                        Constants.ApiComponents.API_HEADER_TYPE
//                        , Constants.ApiComponents.API_KEY_HEADER
//                    )
//                    .addHeader(
//                        Constants.ApiComponents.USER_AGENT_TYPE, Constants
//                            .ApiComponents.USER_AGENT_VALUE
//                    )
                    .build()
                chain.proceed(request)
            })
            .addInterceptor(loggingInterceptor)
            .addInterceptor(NetworkInterceptor())
            .connectTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext application: Context): Cache {
        val cacheSize = 10 * 1024 * 1024.toLong() // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

}