package com.chibisova.vstu.domain.repository

import com.chibisova.vstu.domain.model.Meme
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MemeRepository {

    fun getMemes(): Single<List<Meme>>

    fun getUserMemes(): Single<List<Meme>>

    fun addMeme(meme: Meme): Completable

}