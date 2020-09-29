package com.example.core.module

import android.content.Context
import android.net.ConnectivityManager
import com.example.core.bus.RxBus
import com.example.core.network.APOLApiInterface
import com.example.core.network.ApiInterface
import com.example.core.network.NetworkInterceptor
import com.example.core.network.connectivity.ConnectivityChecker
import com.example.core.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideApiInterface(gson: Gson, client: OkHttpClient, rxAdapter: RxJava2CallAdapterFactory)
            : ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.ApiComponents.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxAdapter)
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(cache: Cache?, rxBus: RxBus): OkHttpClient {
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
            .addInterceptor(NetworkInterceptor(rxBus))
            .connectTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(Constants.DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRxAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideCache(application: Context): Cache {
        val cacheSize = 10 * 1024 * 1024.toLong() // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    @Provides
    fun provideGsonClient(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}