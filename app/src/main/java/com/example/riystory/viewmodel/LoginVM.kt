package com.example.riystory.viewmodel

import androidx.lifecycle.ViewModel
import com.example.riystory.repository.UserRepo

class LoginVM (
    private val repository: UserRepo

) : ViewModel(){
    fun login(email: String, pass: String) = repository.login(email, pass)
}