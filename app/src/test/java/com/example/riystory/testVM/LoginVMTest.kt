package com.example.riystory.testVM

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.riystory.data.response.LoginResponse
import com.example.riystory.repository.UserRepo
import com.example.riystory.testData.UserRepoDummy
import com.example.riystory.viewmodel.LoginVM
import com.example.riystory.data.Result
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginVMTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: UserRepo
    private lateinit var loginViewModel: LoginVM
    private val dummyLogin = UserRepoDummy.dummyLoginResponse()

    @Before
    fun setUp() {
        loginViewModel = LoginVM(storyRepository)
    }

    @Test
    fun `test login`() {
        val expectedUser = MutableLiveData<Result<LoginResponse>>()
        expectedUser.value = Result.Success(dummyLogin)
        Mockito.`when`(storyRepository.login(EMAIL, PASSWORD)).thenReturn(expectedUser)

        val actualUser = loginViewModel.login(EMAIL, PASSWORD).getOrAwaitValue()

        Mockito.verify(storyRepository).login(EMAIL, PASSWORD)
        Assert.assertNotNull(actualUser)
        Assert.assertEquals(Result.Success(dummyLogin), actualUser)
    }

    companion object {
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }
}