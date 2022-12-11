package com.chibisova.vstu.ui

import android.content.Context
import com.chibisova.vstu.App
import com.chibisova.vstu.R
import com.chibisova.vstu.navigation.NavigationStartApp
import com.chibisova.vstu.presenters.SplashPresenter
import com.chibisova.vstu.views.SplashView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class SplashFragment : MvpAppCompatFragment(R.layout.fragment_splash), SplashView {

    @Inject
    lateinit var presenterProvider: Provider<SplashPresenter>
    private val presenter by moxyPresenter {
        presenterProvider.get()
    }

    @Inject
    lateinit var navigationStartApp: NavigationStartApp

    private var isAuthUser: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.getFragmentAuthComponentOrCreateIfNull().inject(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.startApp()
    }

    override fun startApp(isAuthUser: Boolean) {
        this.isAuthUser = isAuthUser
        navigationStartApp.startApp(isAuthUser)
    }

    override fun onDetach() {
        super.onDetach()
        if (isAuthUser) {
            App.instance.clearFragmentAuthComponent()
        }
    }
}