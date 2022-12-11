package com.chibisova.vstu.common

/**
 * Интерфейс для контролирование swipeRefreshLayout
 */
interface RefresherOwner {
    fun setRefresherState(refresherState: Boolean)
}
