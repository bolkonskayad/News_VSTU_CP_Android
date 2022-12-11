package com.chibisova.vstu.presenters

import com.chibisova.vstu.domain.repository.UserRepository
import com.chibisova.vstu.views.SplashView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SplashPresenter @Inject constructor(
    private val userRepository: UserRepository
) : MvpPresenter<SplashView>() {

    fun startApp() {
        userRepository.containsUser()
            .delay(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.startApp(it)
            }, {
                viewState.startApp(false)
            })
    }

}