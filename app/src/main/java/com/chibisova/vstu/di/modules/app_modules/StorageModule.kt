package com.chibisova.vstu.di.modules.app_modules

import android.content.Context
import androidx.room.Room
import com.chibisova.vstu.data.db.NewsDao
import com.chibisova.vstu.data.db.NewsDatabase
import com.chibisova.vstu.data.dto.local.NewsDbo
import com.chibisova.vstu.data.dto.mappers.New.NewDataMapper
import com.chibisova.vstu.data.dto.mappers.New.NewDbDataMapper
import com.chibisova.vstu.data.storage.Storage
import com.chibisova.vstu.data.storage.StorageImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class StorageModule {

    companion object {
        private const val nameDatabase = "New_DATABASE"
    }

    @Provides
    @Singleton
    fun provideDbNewMapper(): NewDataMapper<NewsDbo> = NewDbDataMapper()

    @Provides
    @Singleton
    fun provideDatabase(context: Context): NewsDatabase =
        Room.databaseBuilder(context, NewsDatabase::class.java, nameDatabase)
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideNewDao(database: NewsDatabase): NewsDao = database.newsDao()


    @Provides
    @Singleton
    fun provideStorage(NewsDao: NewsDao, mapperDbo: NewDataMapper<NewsDbo>): Storage =
        StorageImpl(NewsDao, mapperDbo)

}