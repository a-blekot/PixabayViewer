package com.example.pixabayviewer.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: T): Long

    @JvmSuppressWildcards
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objects: List<T>)

    @Delete
    suspend fun delete(t: T)

    @Update
    suspend fun update(t: T)

    companion object {
        const val NO_ID = 0
    }
}
