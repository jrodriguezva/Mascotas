package es.architectcoders.mascotas.ui.profile.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.right
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.architectcoders.data.repository.LoginRepository
import es.architectcoders.domain.Advert
import es.architectcoders.domain.User
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.profile.fragments.ProfileFragment
import es.architectcoders.mascotas.utils.MainCoroutineScopeRule
import es.architectcoders.mascotas.utils.captureValues
import es.architectcoders.mascotas.utils.getValueForTest
import es.architectcoders.usescases.FindAdvertsByAuthor
import es.architectcoders.usescases.GetUser
import es.architectcoders.usescases.SaveUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {

    private val emailMock = "test@a.com"

    private val userMock = User(
        email = emailMock,
        name = "Pepito",
        surname = "Palotes",
        photoUrl = "",
        ratingCount = 1,
        rating = 1,
        level = "Lactante, superaste nivel cachorro pero solo te alimentas de leche materna aún",
        city = "Madrid",
        country = "España")

    private val advertMock = Advert(
        id = "0",
        title = "Nuevo",
        photoBase64 = null,
        price = "2342",
        recent = false,
        author = emailMock
    )

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Mock
    lateinit var findAdvertsByAuthor: FindAdvertsByAuthor

    @Mock
    lateinit var loginRepository: LoginRepository

    @Mock
    lateinit var getUser: GetUser

    @Mock
    lateinit var saveUser: SaveUser

    @Mock
    lateinit var resourceProvider: ResourceProvider

    private lateinit var vm: ProfileViewModel

    private fun createViewModel() {
        vm = ProfileViewModel(findAdvertsByAuthor, loginRepository, getUser, saveUser, resourceProvider, Dispatchers.Unconfined)
    }

    @Test
    fun `get user data`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        whenever(loginRepository.getCurrentUser()).thenReturn(userMock.right())
        whenever(getUser.invoke(emailMock)).thenReturn(userMock)
        createViewModel()
        vm.loading.captureValues {
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, listOf(true, true, false))
            verify(getUser).invoke(emailMock)
            assertNotNull(vm.userData)
        }
    }

    @Test
    fun `refresh to find adverts on sale`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()

        val listAdvertsByAuthor = listOf(advertMock, advertMock, advertMock)
        whenever(findAdvertsByAuthor.invoke(emailMock)).thenReturn(listAdvertsByAuthor)

        whenever(loginRepository.getCurrentUser()).thenReturn(userMock.right())
        whenever(getUser.invoke(emailMock)).thenReturn(userMock)

        createViewModel()
        vm.loading.captureValues {
            assertNull(vm.adverts.value)
            coroutinesTestRule.resumeDispatcher()
            vm.refresh(ProfileFragment.TYPES.ON_SALE)
            assertEquals(values, listOf(true, true, false, true, false))
            assertNotNull(vm.userData)
            assertNotNull(vm.adverts.value)
        }
    }

}