package com.example.pixabayviewer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pixabayviewer.data.db.account.Account
import com.example.pixabayviewer.data.db.account.AccountDao
import com.example.pixabayviewer.data.db.image.Image
import com.example.pixabayviewer.data.db.image.ImageDao

@Database(
    entities = [Image::class, Account::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    abstract fun accountDao(): AccountDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "Images.db"
        ).fallbackToDestructiveMigration().build()
    }
}
