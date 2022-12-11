package com.chibisova.vstu.di.modules.content_modules

import com.chibisova.vstu.data.api.MemesApi
import com.chibisova.vstu.data.dto.mappers.meme.MemeDataMapper
import com.chibisova.vstu.data.dto.network.NetworkMeme
import com.chibisova.vstu.data.repository.MemeRepositoryImpl
import com.chibisova.vstu.data.storage.Storage
import com.chibisova.vstu.di.scopes.FragmentContentScope
import com.chibisova.vstu.domain.repository.MemeRepository
import dagger.Module
import dagger.Provides


@Module
class RepositoryContentModule {

    @Provides
    @FragmentContentScope
    fun provideMemeRepository(
        memesApi: MemesApi,
        mapperNetwork: MemeDataMapper<NetworkMeme>,
        storage: Storage
    ): MemeRepository = MemeRepositoryImpl(memesApi, mapperNetwork, storage)

}