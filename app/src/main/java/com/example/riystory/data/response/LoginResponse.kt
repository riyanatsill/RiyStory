package com.example.riystory.data.response

import com.google.gson.annotations.SerializedName

class LoginResponse (
    @SerializedName("loginResult") val loginResult: LoginResult,
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String
)

data class LoginResult(
    @SerializedName("name") val name: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("token") val token: String
)