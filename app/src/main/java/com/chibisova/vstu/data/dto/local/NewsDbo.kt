package com.chibisova.vstu.data.dto.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsDbo(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "created_date")
    val createdDate: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean,

    @ColumnInfo(name = "photo_url")
    val photoUrl: String,

    @ColumnInfo(name = "is_local_user_created")
    var isLocalUserCreated: Boolean = false
)