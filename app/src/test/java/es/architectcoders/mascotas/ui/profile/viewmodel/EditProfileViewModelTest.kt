package es.architectcoders.mascotas.ui.profile.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.right
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.architectcoders.data.repository.LoginRepository
import es.architectcoders.domain.User
import es.architectcoders.macotas.sharedtest.utils.MockEmail
import es.architectcoders.macotas.sharedtest.utils.loading
import es.architectcoders.macotas.sharedtest.utils.mockUser
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.common.ValidatorUtil
import es.architectcoders.mascotas.utils.*
import es.architectcoders.usescases.GetUser
import es.architectcoders.usescases.SaveUser
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
class EditProfileViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Mock
    lateinit var validatorUtil: ValidatorUtil

    @Mock
    lateinit var loginRepository: LoginRepository

    @Mock
    lateinit var getUser: GetUser

    @Mock
    lateinit var saveUser: SaveUser

    @Mock
    lateinit var resourceProvider: ResourceProvider

    private lateinit var vm: EditProfileViewModel

    private fun createViewModel() {
        vm = EditProfileViewModel(validatorUtil, loginRepository, getUser, saveUser, resourceProvider)
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
    fun `on save when name is not valid should be get error`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()

        whenever(loginRepository.getCurrentUser()).thenReturn(mockUser.right())
        whenever(getUser.invoke(MockEmail)).thenReturn(
            mockUser
        )
        whenever(validatorUtil.validateName(any())).thenReturn(1)

        val textError = "error"
        whenever(resourceProvider.getString(1)).thenReturn(textError)
        val name = "a"
        val surname = "Palotes"
        val city = "Madrid"
        val country = "España"

        createViewModel()

        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            vm.updateUser(name, surname, city, country)
            assertEquals(vm.nameError.getValueForTest(), textError)
            assertEquals(values, loading)
            verify(saveUser, never()).invoke(mockUser)
        }
    }

    @Test
    fun `save user data`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()

        whenever(loginRepository.getCurrentUser()).thenReturn(mockUser.right())
        whenever(getUser.invoke(MockEmail)).thenReturn(
            mockUser
        )
        whenever(saveUser.invoke(mockUser)).thenReturn(User().right())
        whenever(validatorUtil.validateName(any())).thenReturn(null)
        whenever(validatorUtil.validateSurname(any())).thenReturn(null)
        whenever(validatorUtil.validateCity(any())).thenReturn(null)
        whenever(validatorUtil.validateCountry(any())).thenReturn(null)

        val name = "Pepito"
        val surname = "Palotes"
        val city = "Madrid"
        val country = "España"

        createViewModel()

        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            vm.updateUser(name, surname, city, country)
            assertEquals(values, loading + loading)
        }
    }
}
