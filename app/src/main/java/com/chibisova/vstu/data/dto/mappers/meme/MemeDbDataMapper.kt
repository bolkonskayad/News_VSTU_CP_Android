package com.chibisova.vstu.data.dto.mappers.meme

import com.chibisova.vstu.data.dto.local.MemeDbo
import com.chibisova.vstu.domain.model.Meme
import java.util.ArrayList

class MemeDbDataMapper : MemeDataMapper<MemeDbo> {

    override fun transform(memeDbo: MemeDbo) = Meme(
        id = memeDbo.id,
        title = memeDbo.title,
        createdDate = memeDbo.createdDate,
        description = memeDbo.description,
        isFavorite = memeDbo.isFavorite,
        photoUrl = memeDbo.photoUrl
    )

    override fun transformList(list: List<MemeDbo>): List<Meme> {
        val listMeme = ArrayList<Meme>()
        for (meme in list) {
            listMeme.add(
                Meme(
                    id = meme.id,
                    title = meme.title,
                    createdDate = meme.createdDate,
                    description = meme.description,
                    isFavorite = meme.isFavorite,
                    photoUrl = meme.photoUrl
                )
            )
        }
        return listMeme
    }

    override fun reverseTransform(meme: Meme): MemeDbo = MemeDbo(
        id = meme.id,
        title = meme.title,
        createdDate = meme.createdDate,
        description = meme.description,
        isFavorite = meme.isFavorite,
        photoUrl = meme.photoUrl
        )

    override fun reverseTransformList(list: List<Meme>): List<MemeDbo> {
        val listMeme = ArrayList<MemeDbo>()
        for (meme in list) {
            listMeme.add(
                MemeDbo(
                    id = meme.id,
                    title = meme.title,
                    createdDate = meme.createdDate,
                    description = meme.description,
                    isFavorite = meme.isFavorite,
                    photoUrl = meme.photoUrl
                )
            )
        }
        return listMeme
    }
}