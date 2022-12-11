package com.chibisova.vstu.di.modules.app_modules

import android.content.Context
import androidx.room.Room
import com.chibisova.vstu.data.db.MemeDao
import com.chibisova.vstu.data.db.MemeDatabase
import com.chibisova.vstu.data.dto.local.MemeDbo
import com.chibisova.vstu.data.dto.mappers.meme.MemeDataMapper
import com.chibisova.vstu.data.dto.mappers.meme.MemeDbDataMapper
import com.chibisova.vstu.data.storage.Storage
import com.chibisova.vstu.data.storage.StorageImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class StorageModule {

    companion object {
        private const val nameDatabase = "MEME_DATABASE"
    }

    @Provides
    @Singleton
    fun provideDbMemeMapper(): MemeDataMapper<MemeDbo> = MemeDbDataMapper()

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MemeDatabase =
        Room.databaseBuilder(context, MemeDatabase::class.java, nameDatabase)
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideMemeDao(database: MemeDatabase): MemeDao = database.memeDao()


    @Provides
    @Singleton
    fun provideStorage(memeDao: MemeDao, mapperDbo: MemeDataMapper<MemeDbo>): Storage =
        StorageImpl(memeDao, mapperDbo)

}