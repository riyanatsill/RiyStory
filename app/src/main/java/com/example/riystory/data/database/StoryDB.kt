package com.example.riystory.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.riystory.data.response.ListStoryItem
import com.example.riystory.entity.PaginationEntity

@Database(
    entities = [ListStoryItem::class, PaginationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDB : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): PaginationDao

    companion object {
        @Volatile
        private var INSTANCE: StoryDB? = null

        fun getDatabase(context: Context): StoryDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): StoryDB {
            return Room.databaseBuilder(
                context.applicationContext,
                StoryDB::class.java, "story_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}