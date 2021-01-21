package com.example.pixabayviewer.view.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayviewer.data.db.image.Image
import com.example.pixabayviewer.domain.ImageRepository
import com.example.pixabayviewer.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageDetailViewModel @Inject constructor(private val imageRepository: ImageRepository) :
    ViewModel() {

    private val _result: MutableLiveData<Resource<Image>> = MutableLiveData()
    val result: LiveData<Resource<Image>>
        get() = _result

    fun loadImage(imageId: Int) {
        viewModelScope.launch {
            _result.postValue(imageRepository.fetch(imageId))
        }
    }
}