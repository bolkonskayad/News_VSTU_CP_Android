package com.chibisova.vstu

import android.app.Application
import android.content.Context
import com.chibisova.vstu.di.components.*
import com.chibisova.vstu.di.modules.activity_modules.ActivityModule
import com.chibisova.vstu.di.modules.activity_modules.NavigationMainModule
import com.chibisova.vstu.di.modules.app_modules.*
import com.chibisova.vstu.di.modules.auth_modules.PresenterAuthModule
import com.chibisova.vstu.di.modules.content_modules.PresenterContentModule
import com.chibisova.vstu.di.modules.content_modules.RepositoryContentModule
import com.chibisova.vstu.di.modules.content_modules.TabModule
import com.chibisova.vstu.ui.TabFragment

class App : Application() {

    companion object {
        lateinit var instance: App;
    }

    private lateinit var appComponent: AppComponent
    private var activityComponent: ActivityComponent? = null
    private var fragmentContentComponent: FragmentContentComponent? = null
    private var fragmentAuthComponent: FragmentAuthComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .serviceModule(ServiceModule())
            .storageModule(StorageModule())
            .repositoryModule(RepositoryModule())
            .build()
    }

    fun startActivityComponent(activity: Context): ActivityComponent {
        if (activityComponent == null) {
            activityComponent = appComponent.addActivityComponent(
                NavigationMainModule(activity),
                ActivityModule(activity)
            )
        }
        return activityComponent!!
    }

    fun clearActivityComponent() {
        activityComponent = null
    }


    fun getFragmentAuthComponentOrCreateIfNull(): FragmentAuthComponent {
        if (fragmentAuthComponent == null) {
            fragmentAuthComponent = activityComponent?.addFragmentAuthComponent(
                PresenterAuthModule()
            )
        }
        return fragmentAuthComponent!!
    }

    fun clearFragmentAuthComponent(){
        fragmentAuthComponent = null
    }

    fun getFragmentContentComponentOrCreateIfNull(tabFragment: TabFragment? = null): FragmentContentComponent {
        if (fragmentContentComponent == null && tabFragment != null) {
            fragmentContentComponent = activityComponent?.addFragmentContentComponent(
                PresenterContentModule(),
                RepositoryContentModule(),
                TabModule(tabFragment)
            )
        }
        return fragmentContentComponent!!
    }

    fun clearFragmentContentComponent() {
        fragmentContentComponent = null
    }

}