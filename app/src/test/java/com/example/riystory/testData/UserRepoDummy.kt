package com.example.riystory.testData

import com.example.riystory.data.response.ListStoryItem
import com.example.riystory.data.response.LoginResponse
import com.example.riystory.data.response.LoginResult
import com.example.riystory.data.response.RegisterResponse
import com.example.riystory.data.response.StoryResponse
import com.example.riystory.data.response.UploadResponse

object UserRepoDummy {

    fun dummyLoginResponse(): LoginResponse =
        LoginResponse(
            LoginResult("name", "id", "token"),
            false,
            "token"
        )

    fun dummyRegisterResponse(): RegisterResponse =
        RegisterResponse(false, "success")

    fun generateDummyStoryItem(i: Int): ListStoryItem =
        ListStoryItem(
            i.toString(),
            "created + $i",
            "name + $i",
            "description + $i",
            i.toDouble(),
            "id + $i",
            i.toDouble()
        )

    fun dummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = mutableListOf()
        for (i in 0..100) {
            items.add(generateDummyStoryItem(i))
        }
        return items
    }

    fun dummyStoryWithLocationResponse(): StoryResponse {
        val items: MutableList<ListStoryItem> = mutableListOf()
        for (i in 0..100) {
            items.add(generateDummyStoryItem(i))
        }
        return StoryResponse(items, false, "success")
    }

    fun dummyAddStoryResponse(): UploadResponse =
        UploadResponse(false, "success")
}