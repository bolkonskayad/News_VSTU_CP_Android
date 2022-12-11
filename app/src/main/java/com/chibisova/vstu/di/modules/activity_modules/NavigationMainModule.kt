package com.chibisova.vstu.di.modules.activity_modules

import android.content.Context
import com.chibisova.vstu.di.scopes.ActivityScope
import com.chibisova.vstu.navigation.*
import dagger.Module
import dagger.Provides

@Module
class NavigationMainModule(private val activity: Context) {

    @Provides
    @ActivityScope
    fun provideNavigationAuth() = activity as NavigationAuth

    @Provides
    @ActivityScope
    fun provideNavigationContent() = activity as NavigationContent

    @Provides
    @ActivityScope
    fun provideNavigationStartApp() = activity as NavigationStartApp

}