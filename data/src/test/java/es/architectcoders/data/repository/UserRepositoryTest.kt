package es.architectcoders.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.data.datasource.UserDataSource
import es.architectcoders.domain.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {
    @Mock
    lateinit var userDataSource: UserDataSource
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = UserRepository(userDataSource)
    }

    @Test
    fun `add user to firebase`() {

        runBlocking {

            val user = User(
                email = "test@a.com",
                name = "Pepito",
                surname = "Palotes",
                photoUrl = "",
                ratingCount = 1,
                rating = 1,
                level = "Lactante, superaste nivel cachorro pero solo te alimentas de leche materna aún",
                city = "Madrid",
                country = "España")

            userRepository.saveUser(user)
            verify(userDataSource).saveUser(user)
        }
    }

    @Test
    fun `get user from firebase`() {
        runBlocking {

            val user = User(
                email = "test@a.com",
                name = "Pepito",
                surname = "Palotes",
                photoUrl = "",
                ratingCount = 1,
                rating = 1,
                level = "Lactante, superaste nivel cachorro pero solo te alimentas de leche materna aún",
                city = "Madrid",
                country = "España")

            whenever(userDataSource.getUser("test@a.com")).thenReturn(user)
            val result = userRepository.getUser("test@a.com")
            Assert.assertEquals(user, result)
        }
    }


}