package com.chibisova.vstu.presenters

import com.chibisova.vstu.common.base_view.BasePresenter
import com.chibisova.vstu.domain.model.News
import com.chibisova.vstu.domain.repository.NewsRepository
import com.chibisova.vstu.domain.repository.UserRepository
import com.chibisova.vstu.views.ProfileView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val userRepository: UserRepository,
    private val newsRepository: NewsRepository
) : BasePresenter<ProfileView>() {

    fun bindProfile() {
        userRepository.getUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isEmpty) {
                    viewState.showErrorSnackBarDownloadProfile("Ошибка иннициализации профиля")
                } else {
                    viewState.showProfile(it)
                }
            }, {
                viewState.showErrorSnackBarDownloadProfile("Повторите попытку")
            })
    }

    fun loadNews() {
        newsRepository.getUserNews()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoadState() }
            .doFinally { viewState.hideLoadState() }
            .subscribe({
                viewState.showNews(it)
            }, {
                viewState.showErrorSnackBarDownloadProfile("Ошибка загрузки профиля")
            })
    }

    fun startLogoutDialog() {
        viewState.showDialog()
    }

    fun logout() {
        userRepository
            .deleteUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.exitAccount()
            }, {
                viewState.showErrorSnackBarDownloadProfile("Ошибка выхода из аккаунта. Попробуйте еще раз")
            })
    }

    fun openDetails(news: News) {
        viewState.openNewDetails(news)
    }

    fun shareNewInSocialNetwork(news: News) {
        viewState.shareNew(news)
    }


    fun deleteNews(news: News) {
        newsRepository.deleteNews(news)
            .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
            .subscribe()
        loadNews()
    }
}
