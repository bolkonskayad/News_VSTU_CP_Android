package com.chibisova.vstu.di.components

import com.chibisova.vstu.di.modules.content_modules.PresenterContentModule
import com.chibisova.vstu.di.modules.content_modules.RepositoryContentModule
import com.chibisova.vstu.di.modules.content_modules.TabModule
import com.chibisova.vstu.di.scopes.FragmentContentScope
import com.chibisova.vstu.ui.*
import dagger.Subcomponent


@FragmentContentScope
@Subcomponent(
    modules = [PresenterContentModule::class,
        RepositoryContentModule::class, TabModule::class]
)
interface FragmentContentComponent {

    fun inject(addNewsFragment: AddNewsFragment)

    fun inject(NewsDetailsFragment: NewsDetailsFragment)

    fun inject(NewFeedFragment: NewsFeedFragment)

    fun inject(profileFragment: ProfileFragment)

}