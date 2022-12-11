package com.chibisova.vstu.common.managers

import android.graphics.Bitmap

/**
 * Интерфейс, который реализует базовая активность. Нужен для сохранения фотографии в кеш и получения URI
 */
interface FileManager {

    fun saveImg(imgBtmp: Bitmap): String?

}