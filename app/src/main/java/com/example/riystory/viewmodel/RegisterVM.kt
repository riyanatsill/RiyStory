package com.example.riystory.viewmodel

import androidx.lifecycle.ViewModel
import com.example.riystory.repository.UserRepo

class RegisterVM (
    private val repository: UserRepo
) : ViewModel(){
    fun register(name: String, email: String, pass: String) = repository.register(name, email, pass)
}