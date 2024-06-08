package com.example.riystory.testData

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.riystory.data.retrofit.ServiceApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserRepoTest {
    @get:Rule
    private val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    private val mainDispatcherRule = DispatcherRule()

    private lateinit var apiService: ServiceApi

    @Before
    fun setUp() {
        apiService = FakeApi()
    }

    @Test
    fun `test getStories`() = runTest {
        val expectedStory = UserRepoDummy.dummyStoryResponse()
        val actualStory = apiService.getStories(1, 50, TOKEN)
        assertNotNull(actualStory)
        assertEquals(expectedStory.size, actualStory.listStory.size)
    }

    @Test
    fun `test register`() = runTest {
        val expectedUser = UserRepoDummy.dummyRegisterResponse()
        val actualUser = apiService.register(NAME, EMAIL, PASSWORD)
        assertNotNull(actualUser)
        assertEquals(expectedUser.message, actualUser.message)
    }

    @Test
    fun `test login`() = runTest {
        val expectedUser = UserRepoDummy.dummyLoginResponse()
        val actualUser = apiService.login(EMAIL, PASSWORD)
        assertNotNull(actualUser)
        assertEquals(expectedUser.message, actualUser.message)
    }

    @Test
    fun `test getStoryWithLocation`() = runTest {
        val expectedStory = UserRepoDummy.dummyStoryWithLocationResponse()
        val actualStory = apiService.getStoriesWithLocation(1, TOKEN)
        assertNotNull(actualStory)
        assertEquals(expectedStory.listStory.size, actualStory.listStory.size)
    }

    @Test
    fun `test postImage`() = runTest {
        val description = "This is Image Description"
            .toRequestBody("text/plain".toMediaType())

        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart = MultipartBody.Part.createFormData("photo", "nameFile", requestImageFile)

        val expectedStory = UserRepoDummy.dummyAddStoryResponse()
        val actualStory = apiService.postImage(imageMultipart, description, LAT, LON, TOKEN, ACCEPT)
        assertNotNull(actualStory)
        assertEquals(expectedStory.message, actualStory.message)
    }

    companion object {
        private const val TOKEN = "Bearer TOKEN"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val LAT = 1.00
        private const val LON = 1.00
        private const val ACCEPT = "application/json"
    }
}