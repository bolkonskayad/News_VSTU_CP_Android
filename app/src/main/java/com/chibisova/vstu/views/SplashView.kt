package com.chibisova.vstu.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface SplashView: MvpView {

    @Skip
    fun startApp(isAuthUser: Boolean)

}