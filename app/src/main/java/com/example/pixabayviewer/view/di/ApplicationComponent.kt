package com.example.pixabayviewer.view.di

import com.example.pixabayviewer.PixabayViewerApp
import com.example.pixabayviewer.view.ui.detail.ImageDetailFragment
import com.example.pixabayviewer.view.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ApplicationModule::class,
        DataModule::class,
        ApiModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        AndroidInjectionModule::class,
        ActivityBuilder::class
    ]
)
@Singleton
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: PixabayViewerApp
        ): ApplicationComponent
    }

    fun inject(application: PixabayViewerApp)
    fun inject(homeFragment: HomeFragment)
    fun inject(imageDetailsFragment: ImageDetailFragment)
}
