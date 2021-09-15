package com.ricker.qrcodeapp.di

import androidx.room.Room
import com.ricker.qrcodeapp.cache.HistoryDao
import com.ricker.qrcodeapp.cache.database.AppDatabase
import com.ricker.qrcodeapp.cache.model.HistoryEntityMapper
import com.ricker.qrcodeapp.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase{
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideHistoryDao(db: AppDatabase): HistoryDao{
        return db.historyDao()
    }

    @Singleton
    @Provides
    fun provideHistoryCacheMapper(): HistoryEntityMapper{
        return HistoryEntityMapper()
    }

}