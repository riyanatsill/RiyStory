package com.example.riystory.testVM

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.riystory.repository.UserRepo
import com.example.riystory.testData.UserRepoDummy
import com.example.riystory.viewmodel.MapVM
import com.example.riystory.data.Result
import com.example.riystory.data.response.StoryResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapVMTest {

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: UserRepo
    private lateinit var storyWithMapsViewModel: MapVM
    private val dummyStoryWithMaps = UserRepoDummy.dummyStoryWithLocationResponse()

    @Before
    fun setUp() {
        storyWithMapsViewModel = MapVM(storyRepository)
    }

    @Test
    fun `test getStoryWithLocation`() {
        val expectedStory = MutableLiveData<Result<StoryResponse>>()
        expectedStory.value = Result.Success(dummyStoryWithMaps)
        Mockito.`when`(storyRepository.getWithLocation(LOCATION, TOKEN))
            .thenReturn(expectedStory)

        val actualStory =
            storyWithMapsViewModel.getWithLocation(LOCATION, TOKEN).getOrAwaitValue()

        Mockito.verify(storyRepository).getWithLocation(LOCATION, TOKEN)
        Assert.assertNotNull(actualStory)
        Assert.assertTrue(actualStory is Result.Success)
    }

    companion object {
        private const val LOCATION = 1
        private const val TOKEN = "Bearer TOKEN"
    }
}