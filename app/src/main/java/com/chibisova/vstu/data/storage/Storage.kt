package com.chibisova.vstu.data.storage

import com.chibisova.vstu.domain.model.News
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface Storage {

    fun insertUserNew(newsUser: News): Completable

    fun insertNews(newsList: List<News>)

    fun removeNews(): Completable

    fun getAllNews(): Single<List<News>>

    fun getUserNews(): Single<List<News>>

}