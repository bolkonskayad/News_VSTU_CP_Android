package com.chibisova.vstu.data.db

import androidx.room.*
import com.chibisova.vstu.data.dto.local.MemeDbo


@Dao
interface MemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeme(memeDbo: MemeDbo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemeList(memeDbos: List<MemeDbo>)

    @Query("SELECT * FROM MemeDbo")
    fun getAllMemes(): List<MemeDbo>

    @Query("SELECT * FROM MemeDbo WHERE is_local_user_created = 1")
    fun getAllUserMemes(): List<MemeDbo>

    @Query("SELECT * FROM MemeDbo WHERE id = :id")
    fun getMemeById(id: Int): MemeDbo

    @Update
    fun update(memeDbo: MemeDbo)

    @Delete
    fun delete(memeDbo: MemeDbo)

}