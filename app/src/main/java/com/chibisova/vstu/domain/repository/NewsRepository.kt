package com.chibisova.vstu.domain.repository

import com.chibisova.vstu.domain.model.News
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface NewsRepository {

    fun getNews(): Single<List<News>>

    fun getUserNews(): Single<List<News>>

    fun addNews(news: News): Completable

    fun deleteNews(news: News): Completable

}