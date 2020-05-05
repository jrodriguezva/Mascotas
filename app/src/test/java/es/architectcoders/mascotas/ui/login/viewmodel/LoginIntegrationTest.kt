package es.architectcoders.mascotas.ui.login.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.architectcoders.macotas.sharedtest.utils.loading
import es.architectcoders.mascotas.utils.*
import es.architectcoders.usescases.account.CreateAccountInteractor
import es.architectcoders.usescases.login.GetCurrentUserInteractor
import es.architectcoders.usescases.login.SignInInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    private lateinit var vm: LoginViewModel

    @Test
    fun `on init should be called get current user`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        createViewModel()
        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            assertNotNull(vm.nav.getValueForTest())
        }
    }

    @Test
    fun `on createAccount when is valid should be get create account`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        val email = "asd@asd.com"
        val pass = "asdfgh"

        createViewModel()

        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            vm.createAccount(email, pass)
            assertEquals(values, loading + loading)
            assertNotNull(vm.nav.getValueForTest())
        }
    }

    @Test
    fun `on signIn when is valid should be login`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        val email = "asd@asd.com"
        val pass = "asdfgh"

        createViewModel()

        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            vm.signIn(email, pass)
            assertEquals(values, loading + loading)
            assertNotNull(vm.nav.getValueForTest())
        }
    }

    private fun createViewModel() {
        val vmModule = module {
            factory { LoginViewModel(get(), get(), get(), get(), get()) }
            factory { GetCurrentUserInteractor(get()) }
            factory { SignInInteractor(get()) }
            factory { CreateAccountInteractor(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }
}
