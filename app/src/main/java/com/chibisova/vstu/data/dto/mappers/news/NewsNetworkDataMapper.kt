package com.chibisova.vstu.data.dto.mappers.New

import com.chibisova.vstu.data.dto.network.NetworkNews
import com.chibisova.vstu.domain.model.News
import java.util.ArrayList

class NewNetworkDataMapper: NewDataMapper<NetworkNews> {


    override fun transform(New: NetworkNews): News {
        return News(
            New.id.toInt(),
            New.title,
            New.createdDate,
            New.description,
            New.isFavorite,
            New.photoUrl
        )
    }

    override fun transformList(list: List<NetworkNews>): List<News> {
        val listNews = ArrayList<News>()
        for (NewNetwork in list){
            listNews.add(News(
                NewNetwork.id.toInt(),
                NewNetwork.title,
                NewNetwork.createdDate,
                NewNetwork.description,
                NewNetwork.isFavorite,
                NewNetwork.photoUrl
            ))

        }
        return listNews
    }

    override fun reverseTransform(News: News): NetworkNews {
        TODO("Not yet implemented")
    }

    override fun reverseTransformList(list: List<News>): List<NetworkNews> {
        TODO("Not yet implemented")
    }


}
