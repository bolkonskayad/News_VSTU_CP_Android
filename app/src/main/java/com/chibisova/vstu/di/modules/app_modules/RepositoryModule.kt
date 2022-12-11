package com.chibisova.vstu.di.modules.app_modules

import com.chibisova.vstu.data.dto.mappers.UserDtoDataMapper
import com.chibisova.vstu.data.dto.mappers.New.NewDataMapper
import com.chibisova.vstu.data.dto.mappers.New.NewNetworkDataMapper
import com.chibisova.vstu.data.dto.network.NetworkNews
import com.chibisova.vstu.data.repository.UserRepositoryImpl
import com.chibisova.vstu.data.services.local.SharedPreferenceService
import com.chibisova.vstu.data.services.network.AuthService
import com.chibisova.vstu.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserMapper(): UserDtoDataMapper = UserDtoDataMapper()

    @Provides
    @Singleton
    fun provideNetworkNewMapper(): NewDataMapper<NetworkNews> = NewNetworkDataMapper()

    @Provides
    @Singleton
    fun provideUserRepository(
        sharedPreferences: SharedPreferenceService,
        authService: AuthService,
        mapper: UserDtoDataMapper
    ): UserRepository = UserRepositoryImpl(sharedPreferences, authService, mapper)

}