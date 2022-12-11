package com.chibisova.vstu.common.managers

/**
 * Интерфейс, который реализует основная активность. Нужен для старта получения разрешения для галерии
 */
interface PermissionManager {

    //true если разрешение получено
    fun requestPermissionGallery(): Boolean

}