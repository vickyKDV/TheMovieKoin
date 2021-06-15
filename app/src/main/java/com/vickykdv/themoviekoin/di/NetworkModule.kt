package com.vickykdv.themoviekoin.di

import com.vickykdv.themoviekoin.BuildConfig
import com.vickykdv.themoviekoin.BuildConfig.apiKey
import com.vickykdv.themoviekoin.network.ApiService
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { providesHttpLoggingInterceptor() }
    single { providesApiKey() }
    single { providesHttpClient(get(),get()) }
    single { providesHttpAdapter(get()) }
    single { providesApiEndPoint(get()) }
}

fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.NONE
        }
    }
}

fun providesApiKey() : Interceptor = Interceptor { chain ->
    var request: Request = chain.request()
    val url: HttpUrl = request.url.newBuilder()
        .addQueryParameter("api_key", apiKey)
        .build()
    request = request.newBuilder().url(url).build()
    chain.proceed(request)
}

fun providesHttpClient(
    interceptor: HttpLoggingInterceptor,
    apiKey: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder().apply {
        retryOnConnectionFailure(true)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        addInterceptor(interceptor)
//            if(BuildConfig.DEBUG) addInterceptor(chucker)
        addInterceptor(apiKey)
    }.build()
}

fun providesHttpAdapter(client: OkHttpClient): Retrofit {
    return Retrofit.Builder().apply {
        client(client)
        baseUrl(BuildConfig.baseUrl)
        addConverterFactory(GsonConverterFactory.create())
        addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
    }.build()
}

fun providesApiEndPoint(retrofit: Retrofit) : ApiService {
    return retrofit.create(ApiService::class.java)
}
