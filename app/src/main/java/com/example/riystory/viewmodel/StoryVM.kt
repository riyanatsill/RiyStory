package com.example.riystory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.riystory.data.response.ListStoryItem
import com.example.riystory.repository.UserRepo

class StoryVM (
    private val repository: UserRepo
) : ViewModel() {
    fun getStory(token: String): LiveData<PagingData<ListStoryItem>> =
        repository.getStories(token).cachedIn(viewModelScope)
}