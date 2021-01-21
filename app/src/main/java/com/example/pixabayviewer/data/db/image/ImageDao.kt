package com.example.pixabayviewer.data.db.image

import androidx.room.Dao
import androidx.room.Query
import com.example.pixabayviewer.data.db.BaseDao

@Dao
abstract class ImageDao : BaseDao<Image> {
    @Query("SELECT * FROM images WHERE accountId = :accountId")
    abstract suspend fun fetchAll(accountId: Int): List<Image>

    @Query("SELECT * FROM images WHERE id = :id")
    abstract suspend fun fetch(id: Int): Image?
}
