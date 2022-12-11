package com.chibisova.vstu.di.modules.content_modules

import com.chibisova.vstu.di.scopes.FragmentContentScope
import com.chibisova.vstu.domain.repository.NewsRepository
import com.chibisova.vstu.domain.repository.UserRepository
import com.chibisova.vstu.presenters.*
import dagger.Module
import dagger.Provides

@Module
class PresenterContentModule {

    @Provides
    @FragmentContentScope
    fun provideNewFeedPresenter(newsRepository: NewsRepository) =
        NewsFeedPresenter(newsRepository)

    @Provides
    @FragmentContentScope
    fun provideAddNewPresenter(
        newsRepository: NewsRepository
    ) = AddNewsPresenter(newsRepository)

    @Provides
    @FragmentContentScope
    fun provideProfilePresenter(
        userRepository: UserRepository,
        newsRepository: NewsRepository
    ) = ProfilePresenter(userRepository, newsRepository)

    @Provides
    @FragmentContentScope
    fun provideNewDetailsPresenter(
        userRepository: UserRepository
    ) = NewsDetailsPresenter(userRepository)

}