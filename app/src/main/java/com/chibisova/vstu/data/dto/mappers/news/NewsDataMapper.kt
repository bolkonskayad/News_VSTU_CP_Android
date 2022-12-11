package com.chibisova.vstu.data.dto.mappers.New

import com.chibisova.vstu.domain.model.News

interface NewDataMapper<T> {

    fun transform(New: T): News

    fun transformList(list: List<T>): List<News>

    fun reverseTransform(News: News): T

    fun reverseTransformList(list: List<News>): List<T>
}