package com.chibisova.vstu.utils

import java.text.SimpleDateFormat
import java.util.*

private const val SECOND_IN_MSEC = 1000
private const val MINUTE_IN_MSEC = 60000
private const val HOURS_IN_MSEC = 3600000
private const val DAY_IN_MSEC = 86400000
private const val MONTH_IN_MSEC = 2592000000

fun getPostCreateDate(differenceDateMs: Int): String {
    val currentTimeMs = System.currentTimeMillis()
    val datePublic = Date(currentTimeMs - differenceDateMs)

    val calendar = Calendar.getInstance()
    calendar.time = Date()
    val calendarPublic = Calendar.getInstance()
    calendarPublic.time = datePublic

    if (Date(currentTimeMs).before(datePublic)) {
        //TODO Добавить исключение для обработки
        return "Только что"
    }
    return when {
        differenceDateMs <= SECOND_IN_MSEC -> {
            "Только что"
        }
        differenceDateMs < MINUTE_IN_MSEC -> {
            val secondPublished = calendar.get(Calendar.SECOND) - calendarPublic.get(Calendar.SECOND)
            "$secondPublished секунд назад"
        }
        differenceDateMs < HOURS_IN_MSEC -> {
            val minutePublished = calendar.get(Calendar.MINUTE) - calendarPublic.get(Calendar.MINUTE)
            "$minutePublished минут назад"
        }
        differenceDateMs < DAY_IN_MSEC -> {
            val hoursPublished = calendar.get(Calendar.HOUR_OF_DAY) - calendarPublic.get(Calendar.HOUR_OF_DAY)
            "$hoursPublished часов назад"
        }
        differenceDateMs < MONTH_IN_MSEC -> {
            val dayPublished = calendar.get(Calendar.DAY_OF_YEAR) - calendarPublic.get(Calendar.DAY_OF_YEAR)
            "$dayPublished дней назад"
        }
        else -> {
            val date = SimpleDateFormat("yyyy.MM.dd в HH:mm").format(datePublic)
            "Создано $date"
        }
    }
}