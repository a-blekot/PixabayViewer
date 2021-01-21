package com.example.pixabayviewer.view.di

import com.example.pixabayviewer.MainActivity
import com.example.pixabayviewer.view.ui.detail.ImageDetailFragmentProvider
import com.example.pixabayviewer.view.ui.home.HomeFragmentProvider
import com.example.pixabayviewer.view.ui.login.LoginFragmentProvider
import com.example.pixabayviewer.view.ui.register.RegisterFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(
        modules = [
            HomeFragmentProvider::class,
            ImageDetailFragmentProvider::class,
            LoginFragmentProvider::class,
            RegisterFragmentProvider::class
        ]
    )
    internal abstract fun bindMainActivity(): MainActivity
}
