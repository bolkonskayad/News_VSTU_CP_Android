package com.chibisova.vstu.di.modules.content_modules

import com.chibisova.vstu.common.managers.BottomBarVisible
import com.chibisova.vstu.di.scopes.FragmentContentScope
import com.chibisova.vstu.navigation.NavigationBackPressed
import com.chibisova.vstu.navigation.NavigationNewDetails
import com.chibisova.vstu.ui.TabFragment
import dagger.Module
import dagger.Provides


@Module
class TabModule(private val tabFragment: TabFragment) {

    @Provides
    @FragmentContentScope
    fun provideBottomBarVisible(): BottomBarVisible = tabFragment

    @Provides
    @FragmentContentScope
    fun provideNavigationBackPressed(): NavigationBackPressed = tabFragment

    @Provides
    @FragmentContentScope
    fun provideNavigationNewDetails(): NavigationNewDetails = tabFragment
}