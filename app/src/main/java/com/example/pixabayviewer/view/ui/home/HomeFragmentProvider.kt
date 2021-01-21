package com.example.pixabayviewer.view.ui.home

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentProvider {
    @ContributesAndroidInjector
    internal abstract fun provideHomeFragment(): HomeFragment
}