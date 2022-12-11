package com.chibisova.vstu.data.dto.mappers.New

import com.chibisova.vstu.data.dto.local.NewsDbo
import com.chibisova.vstu.domain.model.News
import java.util.ArrayList

class NewDbDataMapper : NewDataMapper<NewsDbo> {

    override fun transform(NewsDbo: NewsDbo) = News(
        id = NewsDbo.id,
        title = NewsDbo.title,
        createdDate = NewsDbo.createdDate,
        description = NewsDbo.description,
        isFavorite = NewsDbo.isFavorite,
        photoUrl = NewsDbo.photoUrl
    )

    override fun transformList(list: List<NewsDbo>): List<News> {
        val listNews = ArrayList<News>()
        for (New in list) {
            listNews.add(
                News(
                    id = New.id,
                    title = New.title,
                    createdDate = New.createdDate,
                    description = New.description,
                    isFavorite = New.isFavorite,
                    photoUrl = New.photoUrl
                )
            )
        }
        return listNews
    }

    override fun reverseTransform(News: News): NewsDbo = NewsDbo(
        id = News.id,
        title = News.title,
        createdDate = News.createdDate,
        description = News.description,
        isFavorite = News.isFavorite,
        photoUrl = News.photoUrl
        )

    override fun reverseTransformList(list: List<News>): List<NewsDbo> {
        val listNew = ArrayList<NewsDbo>()
        for (New in list) {
            listNew.add(
                NewsDbo(
                    id = New.id,
                    title = New.title,
                    createdDate = New.createdDate,
                    description = New.description,
                    isFavorite = New.isFavorite,
                    photoUrl = New.photoUrl
                )
            )
        }
        return listNew
    }
}