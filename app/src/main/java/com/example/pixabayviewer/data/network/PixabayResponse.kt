package com.example.pixabayviewer.data.network

import com.google.gson.annotations.SerializedName

data class PixabayResponse(
    @SerializedName("hits")
    val images: List<ImageResponse>
)

data class ImageResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("user")
    val userName: String,
    @SerializedName("tags")
    val tags: String,

    @SerializedName("largeImageURL")
    val largeImageURL: String,
    @SerializedName("imageWidth")
    val imageWidth: Int,
    @SerializedName("imageHeight")
    val imageHeight: Int,
    @SerializedName("imageSize")
    val imageSize: Int,
    @SerializedName("type")
    val type: String,

    @SerializedName("previewURL")
    val previewURL: String,
    @SerializedName("previewWidth")
    val previewWidth: Int,
    @SerializedName("previewHeight")
    val previewHeight: Int,

    @SerializedName("views")
    val views: Int,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("comments")
    val comments: Int,
    @SerializedName("favorites")
    val favorites: Int,
    @SerializedName("downloads")
    val downloads: Int
)