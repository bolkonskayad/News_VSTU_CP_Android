package com.chibisova.vstu.di.modules.app_modules

import android.content.SharedPreferences
import com.chibisova.vstu.data.api.AuthApi
import com.chibisova.vstu.data.services.local.SharedPreferenceService
import com.chibisova.vstu.data.services.local.SharedPreferenceServiceImpl
import com.chibisova.vstu.data.services.network.AuthService
import com.chibisova.vstu.data.services.network.AuthServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideSharedPreferenceService(sharedPreference: SharedPreferences): SharedPreferenceService =
        SharedPreferenceServiceImpl(sharedPreference)

    @Provides
    @Singleton
    fun provideAuthService(authApi: AuthApi): AuthService = AuthServiceImpl(authApi)

}