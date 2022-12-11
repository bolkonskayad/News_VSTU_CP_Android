package com.chibisova.vstu.presenters

import com.chibisova.vstu.common.base_view.BasePresenter
import com.chibisova.vstu.domain.model.Meme
import com.chibisova.vstu.domain.repository.UserRepository
import com.chibisova.vstu.views.MemeDetailsView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MemeDetailsPresenter @Inject constructor(
    private val userRepository: UserRepository
) : BasePresenter<MemeDetailsView>() {

    var meme: Meme? = null

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

    fun bindMeme() {
        meme?.let {
            viewState.showMeme(it)
        }
    }

    fun shareMeme() {
        meme?.let {
            viewState.shareMeme(it)
        }
    }


}