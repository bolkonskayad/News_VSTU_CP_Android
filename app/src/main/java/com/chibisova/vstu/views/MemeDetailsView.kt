package com.chibisova.vstu.views

import com.chibisova.vstu.domain.model.Meme
import com.chibisova.vstu.domain.model.User
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface MemeDetailsView: MvpView {

    @AddToEndSingle
    fun showUserInfoToolbar(user: User)

    @AddToEndSingle
    fun showErrorStateUserInfoToolbar()

    @AddToEnd
    fun showMeme(data: Meme)

    @Skip
    fun shareMeme(meme: Meme)

}