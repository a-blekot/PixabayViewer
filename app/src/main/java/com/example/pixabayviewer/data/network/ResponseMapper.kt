package com.example.pixabayviewer.data.network

import com.example.pixabayviewer.data.db.image.Image

object ResponseMapper {

    fun convert(response: PixabayResponse, accountId: Int): List<Image> {
        val images = response.images

        return images.map {
            Image(
                id = it.id,
                accountId = accountId,
                userName = it.userName,
                tags = it.tags,
                largeImageURL = it.largeImageURL,
                imageWidth = it.imageWidth,
                imageHeight = it.imageHeight,
                imageSize = it.imageSize,
                type = it.type,
                previewURL = it.previewURL,
                previewWidth = it.previewWidth,
                previewHeight = it.previewHeight,
                views = it.views,
                likes = it.likes,
                comments = it.comments,
                favorites = it.favorites,
                downloads = it.downloads,
                downloadTimestamp = System.currentTimeMillis()
            )
        }
    }
}
