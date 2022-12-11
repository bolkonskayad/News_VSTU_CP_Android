package com.chibisova.vstu.di.components

import com.chibisova.vstu.di.modules.auth_modules.PresenterAuthModule
import com.chibisova.vstu.di.scopes.FragmentAuthScope
import com.chibisova.vstu.ui.AuthFragment
import com.chibisova.vstu.ui.SplashFragment
import dagger.Subcomponent

@FragmentAuthScope
@Subcomponent(modules = [PresenterAuthModule::class])
interface FragmentAuthComponent {

    fun inject(authFragment: AuthFragment)

    fun inject(splashFragment: SplashFragment)

}