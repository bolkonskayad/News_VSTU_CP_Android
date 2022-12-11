package com.chibisova.vstu.views

import com.chibisova.vstu.common.base_view.BaseNewsListView
import com.chibisova.vstu.domain.model.User
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.*

@StateStrategyType(value = AddToEndStrategy::class)
interface ProfileView: BaseNewsListView {

    fun showDialog()

    fun showProfile(user: User)

    @Skip
    fun exitAccount()

    @Skip
    fun showErrorSnackBarDownloadProfile(message: String)

}
