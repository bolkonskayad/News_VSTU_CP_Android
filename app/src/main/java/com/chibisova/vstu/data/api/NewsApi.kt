package com.chibisova.vstu.data.api

import com.chibisova.vstu.data.dto.network.NetworkNews
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

/**
 * Интерфейс для ретрофита. По сути реализует http запросы
 * Данный интерфейс для получения новостей нужен
 */
interface NewsApi {

    @GET("news")
    fun getNews(): Single<List<NetworkNews>>

}