package com.example.riystory.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.riystory.StoryPagination
import com.example.riystory.data.response.RegisterResponse
import com.example.riystory.data.Result
import com.example.riystory.data.database.StoryDB
import com.example.riystory.data.response.ListStoryItem
import com.example.riystory.data.response.LoginResponse
import com.example.riystory.data.response.StoryResponse
import com.example.riystory.data.response.UploadResponse
import com.example.riystory.data.retrofit.ServiceApi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRepo (
    private val storyDatabase: StoryDB,
    private val apiService: ServiceApi
) {
    fun register(
        name: String,
        email: String,
        pass: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, pass)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("register", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, pass: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, pass)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryPagination(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getStories()
            }
        ).liveData
    }

    fun postImage(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double,
        lon: Double,
        token: String,
        multiPort: String,
    ): LiveData<Result<UploadResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postImage(file, description, lat, lon, token, multiPort)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("post_image", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getWithLocation(location: Int, token: String): LiveData<Result<StoryResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.getStoriesWithLocation(location, token)
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("story_map", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }



}