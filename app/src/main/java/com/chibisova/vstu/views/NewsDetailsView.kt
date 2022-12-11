package com.chibisova.vstu.views

import com.chibisova.vstu.domain.model.News
import com.chibisova.vstu.domain.model.User
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface NewDetailsView: MvpView {

    @AddToEndSingle
    fun showUserInfoToolbar(user: User)

    @AddToEndSingle
    fun showErrorStateUserInfoToolbar()

    @AddToEnd
    fun showNew(data: News)

    @Skip
    fun shareNew(News: News)

}