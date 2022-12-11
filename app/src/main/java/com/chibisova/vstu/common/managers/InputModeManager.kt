package com.chibisova.vstu.common.managers

/**
 * Интерефейс, который реализует основная активность, нужен для настройки поведения клавиатуры
 */
interface InputModeManager {

    /**
     * при появлении клавиатры верстка не поднимается
     */
    fun setAdjustPan()


    /**
     * при появлении клавиатры верстка поднимается и не обрезает часть экрана
     */
    fun setAdjustResize()

}