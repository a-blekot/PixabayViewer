package com.example.pixabayviewer.view.di

import android.content.Context
import com.example.pixabayviewer.PixabayViewerApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: PixabayViewerApp): Context {
        return application
    }
}
