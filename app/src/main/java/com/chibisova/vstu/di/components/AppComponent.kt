package com.chibisova.vstu.di.components

import com.chibisova.vstu.di.modules.activity_modules.ActivityModule
import com.chibisova.vstu.di.modules.activity_modules.NavigationMainModule
import com.chibisova.vstu.di.modules.app_modules.*
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AppModule::class, NetworkModule::class,
        ServiceModule::class, StorageModule::class, RepositoryModule::class]
)
interface AppComponent {
    fun addActivityComponent(navigationMainModule: NavigationMainModule,
                             activityModule: ActivityModule
    ): ActivityComponent
}