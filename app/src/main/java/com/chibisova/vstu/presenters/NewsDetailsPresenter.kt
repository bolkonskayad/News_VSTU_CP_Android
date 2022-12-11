package com.chibisova.vstu.presenters

import com.chibisova.vstu.common.base_view.BasePresenter
import com.chibisova.vstu.domain.model.News
import com.chibisova.vstu.domain.repository.UserRepository
import com.chibisova.vstu.views.NewDetailsView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class NewsDetailsPresenter @Inject constructor(
    private val userRepository: UserRepository
) : BasePresenter<NewDetailsView>() {

    var news: News? = null

    fun bindUserInfoToolbar() {
        userRepository.getUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let {
                    viewState.showUserInfoToolbar(it)
                }
            }, {
                viewState.showErrorStateUserInfoToolbar()
            })
    }

    fun bindNew() {
        news?.let {
            viewState.showNew(it)
        }
    }

    fun shareNew() {
        news?.let {
            viewState.shareNew(it)
        }
    }


}