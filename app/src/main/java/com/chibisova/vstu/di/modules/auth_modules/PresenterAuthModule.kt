package com.chibisova.vstu.di.modules.auth_modules

import com.chibisova.vstu.di.scopes.FragmentAuthScope
import com.chibisova.vstu.domain.repository.UserRepository
import com.chibisova.vstu.presenters.AuthPresenter
import com.chibisova.vstu.presenters.SplashPresenter
import dagger.Module
import dagger.Provides


@Module
class PresenterAuthModule {

    @Provides
    @FragmentAuthScope
    fun provideSplashPresenter(userRepository: UserRepository) = SplashPresenter(userRepository)

    @Provides
    @FragmentAuthScope
    fun provideAuthPresenter(userRepository: UserRepository) = AuthPresenter(userRepository)


}