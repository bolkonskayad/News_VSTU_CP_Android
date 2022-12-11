package com.chibisova.vstu.data.storage

import com.chibisova.vstu.domain.model.Meme
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface Storage {

    fun insertUserMeme(memeUser: Meme): Completable

    fun insertMemes(memeList: List<Meme>)

    fun removeMemes(): Completable

    fun getAllMemes(): Single<List<Meme>>

    fun getUserMemes(): Single<List<Meme>>

}