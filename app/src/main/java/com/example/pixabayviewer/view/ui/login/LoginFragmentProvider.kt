package com.example.pixabayviewer.view.ui.login

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginFragmentProvider {
    @ContributesAndroidInjector
    internal abstract fun provideLoginFragment(): LoginFragment
}