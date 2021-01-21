package com.example.pixabayviewer.view.ui.detail

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ImageDetailFragmentProvider {
    @ContributesAndroidInjector
    internal abstract fun provideImageDetailFragment(): ImageDetailFragment
}