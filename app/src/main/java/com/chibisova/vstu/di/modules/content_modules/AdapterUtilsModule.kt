package com.chibisova.vstu.di.modules.content_modules

import com.chibisova.vstu.ui.controllers.MemeController
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.easyadapter.EasyAdapter


@Module
class AdapterUtilsModule {

    @Provides
    fun provideEasyAdapter() = EasyAdapter()

    @Provides
    fun provideMemeController() = MemeController()

}
