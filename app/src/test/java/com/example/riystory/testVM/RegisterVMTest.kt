package com.example.riystory.testVM

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.riystory.repository.UserRepo
import com.example.riystory.testData.UserRepoDummy
import com.example.riystory.viewmodel.RegisterVM
import com.example.riystory.data.Result
import com.example.riystory.data.response.RegisterResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterVMTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: UserRepo
    private lateinit var signupViewModel: RegisterVM
    private val dummyRegister = UserRepoDummy.dummyRegisterResponse()

    @Before
    fun setUp() {
        signupViewModel = RegisterVM(storyRepository)
    }

    @Test
    fun `test register`() {
        val expectedUser = MutableLiveData<Result<RegisterResponse>>()
        expectedUser.value = Result.Success(dummyRegister)
        Mockito.`when`(storyRepository.register(NAME, EMAIL, PASSWORD)).thenReturn(expectedUser)

        val actualUser = signupViewModel.register(NAME, EMAIL, PASSWORD).getOrAwaitValue()

        Mockito.verify(storyRepository).register(NAME, EMAIL, PASSWORD)
        Assert.assertNotNull(actualUser)
        Assert.assertTrue(actualUser is Result.Success)
    }

    companion object {
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }
}