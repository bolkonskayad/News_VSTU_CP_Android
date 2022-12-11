package com.chibisova.vstu.di.modules.content_modules

import com.chibisova.vstu.di.scopes.FragmentContentScope
import com.chibisova.vstu.domain.repository.MemeRepository
import com.chibisova.vstu.domain.repository.UserRepository
import com.chibisova.vstu.presenters.*
import dagger.Module
import dagger.Provides

@Module
class PresenterContentModule {

    @Provides
    @FragmentContentScope
    fun provideMemeFeedPresenter(memeRepository: MemeRepository) =
        MemesFeedPresenter(memeRepository)

    @Provides
    @FragmentContentScope
    fun provideAddMemePresenter(
        memeRepository: MemeRepository
    ) = AddMemePresenter(memeRepository)

    @Provides
    @FragmentContentScope
    fun provideProfilePresenter(
        userRepository: UserRepository,
        memeRepository: MemeRepository
    ) = ProfilePresenter(userRepository, memeRepository)

    @Provides
    @FragmentContentScope
    fun provideMemeDetailsPresenter(
        userRepository: UserRepository
    ) = MemeDetailsPresenter(userRepository)

}