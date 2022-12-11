package com.chibisova.vstu.common.base_view

import com.chibisova.vstu.domain.model.News
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

//Базовый интерфейс вью для экранов, которые имеют списки с новостями
@StateStrategyType(value = AddToEndStrategy::class)
interface BaseNewsListView: MvpView {

    fun showNews(newsList: List<News>)

    @Skip
    fun showLoadState()

    @Skip
    fun hideLoadState()

    @Skip
    fun shareNew(News: News)

    @Skip
    fun openNewDetails(data: News)

}