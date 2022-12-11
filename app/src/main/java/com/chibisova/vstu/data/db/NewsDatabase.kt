package com.chibisova.vstu.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chibisova.vstu.data.dto.local.NewsDbo

/**
 * Абстрактный класс для таблицы новостей
 * Ей
 */
@Database(entities = [NewsDbo::class], version = 1, exportSchema = false)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}