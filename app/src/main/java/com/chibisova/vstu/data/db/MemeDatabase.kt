package com.chibisova.vstu.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chibisova.vstu.data.dto.local.MemeDbo


@Database(entities = [MemeDbo::class], version = 1, exportSchema = false)
abstract class MemeDatabase: RoomDatabase() {
    abstract fun memeDao(): MemeDao
}