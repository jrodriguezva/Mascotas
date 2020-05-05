package es.architectcoders.mascotas.ui.profile.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.right
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.architectcoders.data.repository.LoginRepository
import es.architectcoders.macotas.sharedtest.utils.*
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.profile.fragments.ProfileFragment
import es.architectcoders.mascotas.utils.MainCoroutineScopeRule
import es.architectcoders.mascotas.utils.captureValues
import es.architectcoders.usescases.FindAdvertsByAuthor
import es.architectcoders.usescases.GetUser
import es.architectcoders.usescases.SaveUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
        whenever(loginRepository.getCurrentUser()).thenReturn(mockUser.right())
        whenever(getUser.invoke(MockEmail)).thenReturn(
            mockUser
        )
        createViewModel()
        vm.loading.captureValues {
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            verify(getUser).invoke(MockEmail)
            assertNotNull(vm.userData)
        }
    }

    @Test
    fun `refresh to find adverts on sale`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        whenever(findAdvertsByAuthor.invoke(MockEmail)).thenReturn(listAdvertsByAuthor)
        whenever(loginRepository.getCurrentUser()).thenReturn(mockUser.right())
        whenever(getUser.invoke(MockEmail)).thenReturn(
            mockUser
        )

        createViewModel()
        vm.loading.captureValues {
            assertNull(vm.adverts.value)
            coroutinesTestRule.resumeDispatcher()
            vm.refresh(ProfileFragment.TYPES.ON_SALE)
            assertEquals(values, loading + loading + loading)
            assertNotNull(vm.userData)
            assertNotNull(vm.adverts.value)
        }
    }
}