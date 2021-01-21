package com.example.pixabayviewer.view.di

import com.example.pixabayviewer.PixabayViewerApp

enum class Injector {

    INSTANCE;

    private lateinit var applicationComponent: ApplicationComponent

    fun initialise(application: PixabayViewerApp) {
        applicationComponent = DaggerApplicationComponent.factory().create(application)
        applicationComponent.inject(application)
    }
}
