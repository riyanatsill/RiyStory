package com.example.riystory.viewmodel

import androidx.lifecycle.ViewModel
import com.example.riystory.repository.UserRepo

class MapVM (
    private val repository: UserRepo
) : ViewModel(){
    fun getWithLocation(location: Int, token: String) =
        repository.getWithLocation(location, token)
}