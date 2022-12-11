package com.chibisova.vstu.data.api

import com.chibisova.vstu.data.dto.network.NetworkNews
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface NewsApi {

    @GET("news")
    fun getNews(): Single<List<NetworkNews>>

}