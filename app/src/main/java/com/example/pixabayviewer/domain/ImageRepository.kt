package com.example.pixabayviewer.domain

import com.example.pixabayviewer.data.db.image.Image
import com.example.pixabayviewer.utils.Resource

interface ImageRepository {
    suspend fun fetchAll(): Resource<List<Image>>

    suspend fun fetch(id: Int): Resource<Image>
}
