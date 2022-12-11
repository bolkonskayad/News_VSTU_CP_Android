package com.chibisova.vstu.common.managers

import android.graphics.Bitmap

interface FileManager {

    fun saveImg(imgBtmp: Bitmap): String?

}