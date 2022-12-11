package com.chibisova.vstu.data.repository

import com.chibisova.vstu.data.api.MemesApi
import com.chibisova.vstu.data.dto.mappers.meme.MemeDataMapper
import com.chibisova.vstu.data.dto.network.NetworkMeme
import com.chibisova.vstu.data.storage.Storage
import com.chibisova.vstu.domain.model.Meme
import com.chibisova.vstu.domain.repository.MemeRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MemeRepositoryImpl @Inject constructor(
    private val memesApi: MemesApi,
    private val networkMapper: MemeDataMapper<NetworkMeme>,
    private val storage: Storage
) : MemeRepository {

    //Получаем список мемов с сервера, добавляем в бд данные и берем поток уже с бд
    override fun getMemes(): Single<List<Meme>> = memesApi
        .getMemes()
        .map { networkMapper.transformList(it) }
        .doOnSuccess {
            storage.insertMemes(it)
        }
        .flatMap { storage.getAllMemes() }
        .subscribeOn(Schedulers.io())

    //Получаем список мемов, созданных локально
    override fun getUserMemes(): Single<List<Meme>> = storage.getUserMemes()

    override fun addMeme(meme: Meme) = storage.insertUserMeme(meme)


}

