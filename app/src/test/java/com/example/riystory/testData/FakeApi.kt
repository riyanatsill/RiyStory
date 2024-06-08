package com.example.riystory.testData

import com.example.riystory.data.response.LoginResponse
import com.example.riystory.data.response.RegisterResponse
import com.example.riystory.data.response.StoryResponse
import com.example.riystory.data.response.UploadResponse
import com.example.riystory.data.retrofit.ServiceApi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApi : ServiceApi {

    private val dummyRegisterResponse = UserRepoDummy.dummyRegisterResponse()
    private val dummyLoginResponse = UserRepoDummy.dummyLoginResponse()
    private val dummyStoryResponse = UserRepoDummy.dummyStoryWithLocationResponse()
    private val dummyAddNewStoryResponse = UserRepoDummy.dummyAddStoryResponse()

    override suspend fun login(email: String, password: String): LoginResponse =
        dummyLoginResponse

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): RegisterResponse =
        dummyRegisterResponse

    override suspend fun getStories(page: Int, size: Int, token: String): StoryResponse =
        dummyStoryResponse

    override suspend fun getStoriesWithLocation(loc: Int, token: String): StoryResponse =
        dummyStoryResponse

    override suspend fun postImage(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double?,
        lon: Double?,
        token: String,
        type: String,
    ): UploadResponse =
        dummyAddNewStoryResponse
}