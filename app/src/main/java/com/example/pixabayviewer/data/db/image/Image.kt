package com.example.pixabayviewer.data.db.image

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Image(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "accountId")
    val accountId: Int,

    @ColumnInfo(name = "userName")
    val userName: String,
    @ColumnInfo(name = "tags")
    val tags: String,

    @ColumnInfo(name = "largeImageURL")
    val largeImageURL: String,
    @ColumnInfo(name = "imageWidth")
    val imageWidth: Int,
    @ColumnInfo(name = "imageHeight")
    val imageHeight: Int,
    @ColumnInfo(name = "imageSize")
    val imageSize: Int,
    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "previewURL")
    val previewURL: String,
    @ColumnInfo(name = "previewWidth")
    val previewWidth: Int,
    @ColumnInfo(name = "previewHeight")
    val previewHeight: Int,

    @ColumnInfo(name = "views")
    val views: Int,
    @ColumnInfo(name = "likes")
    val likes: Int,
    @ColumnInfo(name = "comments")
    val comments: Int,
    @ColumnInfo(name = "favorites")
    val favorites: Int,
    @ColumnInfo(name = "downloads")
    val downloads: Int,

    @ColumnInfo(name = "download_timestamp")
    val downloadTimestamp: Long
)