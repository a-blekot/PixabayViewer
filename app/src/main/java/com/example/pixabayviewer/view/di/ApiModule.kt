package com.example.pixabayviewer.view.di

import com.example.pixabayviewer.data.network.PixabayApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@Suppress("UtilityClassWithPublicConstructor")
abstract class ApiModule {

    companion object {

        @Provides
        @Singleton
        fun providePixabayAPI(retrofit: Retrofit): PixabayApi {
            return retrofit.create(PixabayApi::class.java)
        }
    }
}
