package es.architectcoders.data.repository

import arrow.core.left
import arrow.core.orNull
import arrow.core.right
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import es.architectcoders.data.datasource.LoginDataSource
import es.architectcoders.domain.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryTest {
    @Mock
    lateinit var loginDataSource: LoginDataSource
    private lateinit var loginRepository: LoginRepository

    @Before
    fun setUp() {
        loginRepository = LoginRepository(loginDataSource)
    }

    @Test
    fun `on sign in when is correct should return true`() {
        runBlocking {
            whenever(loginDataSource.signIn(any(), any())).thenReturn(true.right())
            val result = loginRepository.signIn("", "")
            assertEquals(result.isRight(), true)
            assertEquals(result.orNull(), true)
        }
    }

    @Test
    fun `on sign in when is exception should be left`() {
        runBlocking {
            whenever(loginDataSource.signIn(any(), any())).thenReturn(ErrorLoginRepository.AuthenticationError(null).left())
            val result = loginRepository.signIn("", "")
            assertEquals(result.isLeft(), true)
        }
    }

    @Test
    fun `on create account when is correct should return true`() {
        runBlocking {
            whenever(loginDataSource.createAccount(any(), any())).thenReturn(true.right())
            val result = loginRepository.createAccount("", "")
            assertEquals(result.isRight(), true)
            assertEquals(result.orNull(), true)
        }
    }

    @Test
    fun `on create account when is exception should be left`() {
        runBlocking {
            whenever(loginDataSource.createAccount(any(), any())).thenReturn(
                ErrorLoginRepository.AuthenticationError(null).left()
            )
            val result = loginRepository.createAccount("", "")
            assertEquals(result.isLeft(), true)
        }
    }

    @Test
    fun `on get current user when is correct should return user`() {
        runBlocking {
            val user = mock<User>()
            whenever(loginDataSource.getCurrentUser()).thenReturn(user.right())
            val result = loginRepository.getCurrentUser()
            assertEquals(result.isRight(), true)
            assertEquals(result.orNull(), user)
        }
    }

    @Test
    fun `on get current user is exception should be left`() {
        runBlocking {
            whenever(loginDataSource.getCurrentUser()).thenReturn(ErrorLoginRepository.UserNotFoundError.left())
            val result = loginRepository.getCurrentUser()
            assertEquals(result.isLeft(), true)
        }
    }
}