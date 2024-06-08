package com.example.riystory.injection

import android.content.Context
import com.example.riystory.data.database.StoryDB
import com.example.riystory.data.retrofit.ConfigApi
import com.example.riystory.repository.UserRepo

object Data {
    fun provideRepository(context: Context): UserRepo {
        val database = StoryDB.getDatabase(context)
        val apiService = ConfigApi.getApiService()
        return UserRepo(database, apiService)
    }
}