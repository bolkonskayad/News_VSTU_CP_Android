package com.chibisova.vstu.di.modules.app_modules

import com.chibisova.vstu.BuildConfig
import com.chibisova.vstu.data.api.AuthApi
import com.chibisova.vstu.data.api.NewsApi
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    private val BASE_URL = "https://r2.mocker.surfstudio.ru/android_vgtu_news/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        builder.build()
    } else {
        OkHttpClient()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) // need for interceptors
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideNewApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)


}