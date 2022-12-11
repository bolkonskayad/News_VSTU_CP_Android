package com.chibisova.vstu.data.db

import androidx.room.*
import com.chibisova.vstu.data.dto.local.NewsDbo


@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNew(NewsDbo: NewsDbo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewList(newsDb: List<NewsDbo>)

    @Query("SELECT * FROM NewsDbo")
    fun getAllNews(): List<NewsDbo>

    @Query("SELECT * FROM NewsDbo WHERE is_local_user_created = 1")
    fun getAllUserNews(): List<NewsDbo>

    @Query("SELECT * FROM NewsDbo WHERE id = :id")
    fun getNewById(id: Int): NewsDbo

    @Update
    fun update(NewsDbo: NewsDbo)

    @Delete
    fun delete(NewsDbo: NewsDbo)

}