package com.chibisova.vstu.di.modules.content_modules

import com.chibisova.vstu.data.api.NewsApi
import com.chibisova.vstu.data.dto.mappers.New.NewDataMapper
import com.chibisova.vstu.data.dto.network.NetworkNews
import com.chibisova.vstu.data.repository.NewsRepositoryImpl
import com.chibisova.vstu.data.storage.Storage
import com.chibisova.vstu.di.scopes.FragmentContentScope
import com.chibisova.vstu.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides


@Module
class RepositoryContentModule {

    @Provides
    @FragmentContentScope
    fun provideNewRepository(
        NewsApi: NewsApi,
        mapperNetwork: NewDataMapper<NetworkNews>,
        storage: Storage
    ): NewsRepository = NewsRepositoryImpl(NewsApi, mapperNetwork, storage)

}