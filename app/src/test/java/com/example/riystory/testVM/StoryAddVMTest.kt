package com.example.riystory.testVM

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.riystory.repository.UserRepo
import com.example.riystory.testData.UserRepoDummy
import com.example.riystory.viewmodel.StoryAddVM
import com.example.riystory.data.Result
import com.example.riystory.data.response.UploadResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class StoryAddVMTest {

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: UserRepo
    private lateinit var addViewModel: StoryAddVM
    private val dummyAddStory = UserRepoDummy.dummyAddStoryResponse()

    @Before
    fun setUp() {
        addViewModel = StoryAddVM(storyRepository)
    }

    @Test
    fun postImage() {
        val description = "This is Description".toRequestBody("text/plain".toMediaType())
        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            "nameFile",
            requestImageFile
        )

        val expectedStory = MutableLiveData<Result<UploadResponse>>().apply {
            value = Result.Success(dummyAddStory)
        }

        Mockito.`when`(
            storyRepository.postImage(
                imageMultipart, description, LAT, LON, TOKEN, ACCEPT
            )
        ).thenReturn(expectedStory)

        val actualStory = addViewModel.postImage(
            imageMultipart, description, LAT, LON, TOKEN, ACCEPT
        ).getOrAwaitValue()

        Mockito.verify(storyRepository).postImage(
            imageMultipart, description, LAT, LON, TOKEN, ACCEPT
        )

        assert(actualStory is Result.Success)
    }

    companion object {
        private const val TOKEN = "Bearer TOKEN"
        private const val LAT = 1.00
        private const val LON = 1.00
        private const val ACCEPT = "application/json"
    }
}