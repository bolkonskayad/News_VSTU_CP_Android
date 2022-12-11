package com.chibisova.vstu.di.components

import com.chibisova.vstu.di.modules.activity_modules.NavigationMainModule
import com.chibisova.vstu.di.modules.content_modules.PresenterContentModule
import com.chibisova.vstu.di.modules.activity_modules.ActivityModule
import com.chibisova.vstu.di.modules.auth_modules.PresenterAuthModule
import com.chibisova.vstu.di.modules.content_modules.RepositoryContentModule
import com.chibisova.vstu.di.modules.content_modules.TabModule
import com.chibisova.vstu.di.scopes.ActivityScope
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [NavigationMainModule::class, ActivityModule::class])
interface ActivityComponent {

    fun addFragmentContentComponent(presenterContentModule: PresenterContentModule,
                                    repositoryContentModule: RepositoryContentModule,
                                    tabModule: TabModule
    ): FragmentContentComponent

    fun addFragmentAuthComponent(
        presenterAuthModule: PresenterAuthModule
    ): FragmentAuthComponent


}