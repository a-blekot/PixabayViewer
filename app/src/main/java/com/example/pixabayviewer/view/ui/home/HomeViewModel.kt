package com.example.pixabayviewer.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pixabayviewer.data.db.image.Image
import com.example.pixabayviewer.data.network.state.NetworkMonitor
import com.example.pixabayviewer.domain.ImageRepository
import com.example.pixabayviewer.utils.Resource
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val networkMonitor: NetworkMonitor
) :
    ViewModel() {

    val isConnected: LiveData<Boolean>
        get() = networkMonitor.isConnected

    var images: LiveData<Resource<List<Image>>> = liveData {
        emit(Resource.loading())
        emit(imageRepository.fetchAll())
    }

    fun tryLoad() {
        images = liveData {
            emit(Resource.loading())
            emit(imageRepository.fetchAll())
        }
    }
}