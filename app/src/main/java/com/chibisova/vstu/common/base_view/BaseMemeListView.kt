package com.chibisova.vstu.common.base_view

import com.chibisova.vstu.domain.model.Meme
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

@StateStrategyType(value = AddToEndStrategy::class)
interface BaseMemeListView: MvpView {

    fun showMemes(memeList: List<Meme>)

    @Skip
    fun showLoadState()

    @Skip
    fun hideLoadState()

    @Skip
    fun shareMeme(meme: Meme)

    @Skip
    fun openMemeDetails(data: Meme)

}