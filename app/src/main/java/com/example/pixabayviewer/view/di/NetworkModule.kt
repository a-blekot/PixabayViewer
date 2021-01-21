package com.example.pixabayviewer.view.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.pixabayviewer.data.network.state.*
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@Suppress("UtilityClassWithPublicConstructor")
abstract class NetworkModule {

    companion object {

        @Provides
        @Singleton
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://pixabay.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        @Provides
        @Singleton
        fun provideNetworkState(): NetworkState {
            return NetworkStateImpl()
        }

        @Provides
        @Singleton
        fun provideNetworkCallBack(networkState: NetworkState): ConnectivityManager.NetworkCallback {
            return NetworkCallbackImpl(networkState)
        }

        @Provides
        @Singleton
        fun provideNetworkMonitor(
            context: Context,
            networkState: NetworkState,
            networkCallback: ConnectivityManager.NetworkCallback
        ): NetworkMonitor {
            return NetworkMonitorImpl(context, networkState, networkCallback)
        }
    }
}
