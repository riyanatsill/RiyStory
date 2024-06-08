package com.example.riystory.data.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String
)