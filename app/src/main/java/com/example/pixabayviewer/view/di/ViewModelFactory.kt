/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.pixabayviewer.view.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pixabayviewer.data.network.state.NetworkMonitor
import com.example.pixabayviewer.domain.AccountRepository
import com.example.pixabayviewer.domain.ImageRepository
import com.example.pixabayviewer.view.ui.detail.ImageDetailViewModel
import com.example.pixabayviewer.view.ui.home.HomeViewModel
import com.example.pixabayviewer.view.ui.login.LoginViewModel
import com.example.pixabayviewer.view.ui.register.RegisterViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModels[modelClass]?.get() as? T
            ?: throw IllegalArgumentException(
                "The requested ViewModel isn't bound: ${modelClass.simpleName}"
            )
    }
}

@Target(AnnotationTarget.FUNCTION)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
class ViewModelModule {

    @Provides
    fun getViewModelFactory(
        viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(viewModels)
    }

    @Provides
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun homeViewModel(imageRepository: ImageRepository, networkMonitor: NetworkMonitor): ViewModel {
        return HomeViewModel(imageRepository, networkMonitor)
    }

    @Provides
    @IntoMap
    @ViewModelKey(ImageDetailViewModel::class)
    fun imageDetailViewModel(imageRepository: ImageRepository): ViewModel {
        return ImageDetailViewModel(imageRepository)
    }

    @Provides
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun loginViewModel(accountRepository: AccountRepository): ViewModel {
        return LoginViewModel(accountRepository)
    }

    @Provides
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun registerViewModel(accountRepository: AccountRepository): ViewModel {
        return RegisterViewModel(accountRepository)
    }
}
