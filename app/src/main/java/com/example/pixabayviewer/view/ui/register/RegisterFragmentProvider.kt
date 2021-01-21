package com.example.pixabayviewer.view.ui.register

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RegisterFragmentProvider {
    @ContributesAndroidInjector
    internal abstract fun provideRegisterFragment(): RegisterFragment
}