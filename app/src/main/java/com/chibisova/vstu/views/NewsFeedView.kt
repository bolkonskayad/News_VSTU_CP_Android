package com.chibisova.vstu.views

import com.chibisova.vstu.common.base_view.BaseNewsListView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.*

@StateStrategyType(value = AddToEndStrategy::class)
interface NewsFeedView: BaseNewsListView {

    fun showErrorState()

    fun showEmptyFilterErrorState()

    @Skip
    fun showRefresh()

    @Skip
    fun hideRefresh()

    fun enableSearchView()

    @SingleState
    fun disableSearchView()

    @Skip
    fun showErrorSnackbar(message: String)

}
