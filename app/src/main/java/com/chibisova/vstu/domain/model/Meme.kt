package com.chibisova.vstu.domain.model

import java.io.Serializable

data class Meme(
    val id: Int,
    val title: String,
    val createdDate: Int,
    val description: String,
    val isFavorite: Boolean,
    val photoUrl: String
): Serializable