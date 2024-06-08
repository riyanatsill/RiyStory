package com.example.riystory.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.riystory.injection.Data
import com.example.riystory.repository.UserRepo

class FactoryVM (
    private val repository: UserRepo
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterVM::class.java) -> {
                RegisterVM(repository) as T
            }
            modelClass.isAssignableFrom(LoginVM::class.java) -> {
                LoginVM(repository) as T
            }
            modelClass.isAssignableFrom(StoryVM::class.java) -> {
                StoryVM(repository) as T
            }
            modelClass.isAssignableFrom(StoryAddVM::class.java) -> {
                StoryAddVM (repository) as T
            }
            modelClass.isAssignableFrom(MapVM::class.java) -> {
                MapVM (repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: FactoryVM? = null
        fun getInstance(context: Context): FactoryVM =
            instance ?: synchronized(this) {
                instance ?: FactoryVM(Data.provideRepository(context))
            }.also {
                instance = it
            }
    }

}