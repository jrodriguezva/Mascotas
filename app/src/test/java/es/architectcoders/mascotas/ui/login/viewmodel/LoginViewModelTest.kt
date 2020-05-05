package es.architectcoders.mascotas.ui.login.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.right
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.architectcoders.domain.User
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.common.ValidatorUtil
import es.architectcoders.mascotas.utils.MainCoroutineScopeRule
import es.architectcoders.mascotas.utils.captureValues
import es.architectcoders.mascotas.utils.getValueForTest
import es.architectcoders.macotas.sharedtest.utils.loading
import es.architectcoders.usescases.account.CreateAccountInteractor
import es.architectcoders.usescases.login.GetCurrentUserInteractor
import es.architectcoders.usescases.login.SignInInteractor
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
class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Mock
    lateinit var resourceProvider: ResourceProvider

    @Mock
    lateinit var signInInteractor: SignInInteractor

    @Mock
    lateinit var validatorUtil: ValidatorUtil

    @Mock
    lateinit var getCurrentUserInteractor: GetCurrentUserInteractor

    @Mock
    lateinit var createAccountInteractor: CreateAccountInteractor

    private lateinit var vm: LoginViewModel

    @Test
    fun `on init should be get current user`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        whenever(getCurrentUserInteractor()).thenReturn(User().right())
        createViewModel()
        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            verify(getCurrentUserInteractor).invoke()
            assertNotNull(vm.nav.getValueForTest())
        }
    }

    @Test
    fun `on createAccount when is valid should be get create account`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        whenever(createAccountInteractor.invoke(any(), any())).thenReturn(true.right())
        whenever(getCurrentUserInteractor.invoke()).thenReturn(User().right())
        whenever(validatorUtil.validateEmail(any())).thenReturn(null)
        whenever(validatorUtil.validatePassword(any())).thenReturn(null)
        val email = "asd@asd.com"
        val pass = "asdfgh"

        createViewModel()

        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            vm.createAccount(email, pass)
            assertEquals(values, loading + loading)
            verify(createAccountInteractor).invoke(email, pass)
            assertNotNull(vm.nav.getValueForTest())
        }
    }

    @Test
    fun `on signIn when is valid should be login`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        whenever(signInInteractor.invoke(any(), any())).thenReturn(true.right())
        whenever(getCurrentUserInteractor.invoke()).thenReturn(User().right())
        whenever(validatorUtil.validateEmail(any())).thenReturn(null)
        whenever(validatorUtil.validatePassword(any())).thenReturn(null)
        val email = "asd@asd.com"
        val pass = "asdfgh"

        createViewModel()

        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            vm.signIn(email, pass)
            assertEquals(values, loading + loading)
            verify(signInInteractor).invoke(email, pass)
            assertNotNull(vm.nav.getValueForTest())
        }
    }

    @Test
    fun `on signIn when password is not valid should be get error`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        whenever(getCurrentUserInteractor.invoke()).thenReturn(User().right())
        whenever(validatorUtil.validateEmail(any())).thenReturn(null)
        whenever(validatorUtil.validatePassword(any())).thenReturn(1)
        val textError = "error"
        whenever(resourceProvider.getString(1)).thenReturn(textError)
        val email = "asd@asd.com"
        val pass = "asdfgh"

        createViewModel()

        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            vm.signIn(email, pass)
            assertEquals(values, loading)
            verify(signInInteractor, never()).invoke(email, pass)
        }
    }

    @Test
    fun `on createAccount when email in not valid should be get error`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        whenever(getCurrentUserInteractor.invoke()).thenReturn(User().right())
        whenever(validatorUtil.validateEmail(any())).thenReturn(1)
        val textError = "Random"
        whenever(resourceProvider.getString(1)).thenReturn(textError)
        val email = "asd@asd.com"
        val pass = "asdfgh"

        createViewModel()

        vm.loading.captureValues {
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            vm.createAccount(email, pass)
            assertEquals(values, loading)
            assertEquals(vm.emailError.getValueForTest(), textError)
            verify(createAccountInteractor, never()).invoke(email, pass)
        }
    }

    private fun createViewModel() {
        vm = LoginViewModel(signInInteractor, getCurrentUserInteractor, createAccountInteractor, validatorUtil, resourceProvider)
    }
}