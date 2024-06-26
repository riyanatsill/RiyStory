package com.example.riystory.data.retrofit

import com.example.riystory.data.response.LoginResponse
import com.example.riystory.data.response.RegisterResponse
import com.example.riystory.data.response.Response
import com.example.riystory.data.response.StoryResponse
import com.example.riystory.data.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ServiceApi {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("/login")
    suspend fun login2(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") token: String
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun postImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?,
        @Header("Authorization") token: String,
        @Header("Accept") type: String
    ): UploadResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") loc: Int,
        @Header("Authorization") token: String
    ): StoryResponse
}