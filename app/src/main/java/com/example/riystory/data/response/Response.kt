package com.example.riystory.data.response

data class Response(
	val data: Data? = null,
	val message: String? = null
)

data class Data(
	val token: String? = null
)

