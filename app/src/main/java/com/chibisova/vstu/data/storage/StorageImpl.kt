package com.chibisova.vstu.data.storage

import com.chibisova.vstu.data.db.NewsDao
import com.chibisova.vstu.data.dto.local.NewsDbo
import com.chibisova.vstu.data.dto.mappers.New.NewDataMapper
import com.chibisova.vstu.domain.model.News
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class StorageImpl @Inject constructor(
    private val dao: NewsDao,
    private val mapper: NewDataMapper<NewsDbo>
) : Storage {

    override fun insertUserNew(newsUser: News): Completable = Completable.fromCallable {
        val dbNew = mapper.reverseTransform(newsUser)
        dbNew.isLocalUserCreated = true
        dao.insertNew(dbNew)
    }.subscribeOn(Schedulers.io())


    override fun insertNews(newsList: List<News>) {
        val dbNewList = mapper.reverseTransformList(newsList)
        dao.insertNewList(dbNewList)
    }

    override fun getAllNews(): Single<List<News>> = Single.fromCallable {
        val dbNewList = dao.getAllNews()
        mapper.transformList(dbNewList)
    }.subscribeOn(Schedulers.io())


    override fun getUserNews(): Single<List<News>> = Single.fromCallable {
        val dbNewList = dao.getAllUserNews()
        mapper.transformList(dbNewList)
    }.subscribeOn(Schedulers.io())

    override fun deleteNews(news: News): Completable {
        return Completable.fromCallable {
            dao.deleteByTitle(news.title)
        }
    }

    override fun removeNews(): Completable {
        return Completable.complete()
    }


}