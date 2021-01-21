package com.example.pixabayviewer.view.di

import android.content.Context
import com.example.pixabayviewer.data.AccountRepositoryImpl
import com.example.pixabayviewer.data.ImageRepositoryImpl
import com.example.pixabayviewer.data.db.AppDatabase
import com.example.pixabayviewer.data.db.account.AccountDao
import com.example.pixabayviewer.data.db.image.ImageDao
import com.example.pixabayviewer.data.network.PixabayApi
import com.example.pixabayviewer.data.network.state.NetworkMonitor
import com.example.pixabayviewer.domain.AccountRepository
import com.example.pixabayviewer.domain.ImageRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Suppress("UtilityClassWithPublicConstructor")
abstract class DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideRoomDatabase(context: Context): AppDatabase {
            return AppDatabase.getInstance(context)
        }

        @Provides
        @Singleton
        fun provideAccountDao(appDatabase: AppDatabase): AccountDao {
            return appDatabase.accountDao()
        }

        @Provides
        @Singleton
        fun provideImageDao(appDatabase: AppDatabase): ImageDao {
            return appDatabase.imageDao()
        }

        @Provides
        @Singleton
        fun provideAccountRepository(accountDao: AccountDao): AccountRepository {
            return AccountRepositoryImpl(accountDao)
        }

        @Provides
        @Singleton
        fun provideImageRepository(
            networkMonitor: NetworkMonitor,
            accountRepository: AccountRepository,
            pixabayApi: PixabayApi,
            imageDao: ImageDao
        ): ImageRepository {
            return ImageRepositoryImpl(networkMonitor, accountRepository, pixabayApi, imageDao)
        }
    }
}
