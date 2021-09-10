package com.ricker.qrcodeapp.di

import androidx.room.Update
import com.ricker.qrcodeapp.cache.HistoryDao
import com.ricker.qrcodeapp.cache.model.HistoryEntityMapper
import com.ricker.qrcodeapp.interactors.history.GetFavorite
import com.ricker.qrcodeapp.interactors.history.GetHistory
import com.ricker.qrcodeapp.interactors.history.InsertHistory
import com.ricker.qrcodeapp.interactors.history.UpdateHistory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideGetHistory(
        historyDao: HistoryDao,
        entityMapper: HistoryEntityMapper,
    ): GetHistory{
        return GetHistory(
            historyDao = historyDao,
            entityMapper = entityMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideInsertHistory(
        historyDao: HistoryDao,
        entityMapper: HistoryEntityMapper,
    ): InsertHistory{
        return InsertHistory(
            historyDao = historyDao,
            entityMapper = entityMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideUpdateHistory(
        historyDao: HistoryDao,
    ): UpdateHistory{
        return UpdateHistory(
            historyDao = historyDao,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetFavorite(
        historyDao: HistoryDao,
        entityMapper: HistoryEntityMapper,
    ): GetFavorite{
        return GetFavorite(
            historyDao = historyDao,
            entityMapper = entityMapper,
        )
    }

}