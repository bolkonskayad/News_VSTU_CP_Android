package com.chibisova.vstu.di.modules.app_modules

import com.chibisova.vstu.data.dto.mappers.UserDtoDataMapper
import com.chibisova.vstu.data.dto.mappers.meme.MemeDataMapper
import com.chibisova.vstu.data.dto.mappers.meme.MemeNetworkDataMapper
import com.chibisova.vstu.data.dto.network.NetworkMeme
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
    fun provideNetworkMemeMapper(): MemeDataMapper<NetworkMeme> = MemeNetworkDataMapper()

    @Provides
    @Singleton
    fun provideUserRepository(
        sharedPreferences: SharedPreferenceService,
        authService: AuthService,
        mapper: UserDtoDataMapper
    ): UserRepository = UserRepositoryImpl(sharedPreferences, authService, mapper)

}