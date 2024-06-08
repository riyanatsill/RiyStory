package com.example.riystory.viewmodel

import androidx.lifecycle.ViewModel
import com.example.riystory.repository.UserRepo
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryAddVM (
    private val repository: UserRepo
) : ViewModel() {

    fun postImage(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double,
        lon: Double,
        token: String,
        multiPort: String
    ) = repository.postImage(file, description, lat, lon, token, multiPort)
}