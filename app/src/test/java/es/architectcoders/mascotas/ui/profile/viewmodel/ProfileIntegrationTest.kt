package es.architectcoders.mascotas.ui.profile.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.architectcoders.macotas.sharedtest.utils.loading
import es.architectcoders.mascotas.ui.profile.fragments.ProfileFragment
import es.architectcoders.mascotas.utils.*
import es.architectcoders.usescases.FindAdvertsByAuthor
import es.architectcoders.usescases.GetUser
import es.architectcoders.usescases.SaveUser
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
import kotlin.test.assertNull


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProfileIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    private lateinit var vm: ProfileViewModel

    private fun createViewModel() {
        val vmModule = module {
            factory { ProfileViewModel(get(), get(), get(), get(), get(), get()) }
            factory { FindAdvertsByAuthor(get()) }
            //factory { LoginRepository(get()) }
            factory { GetUser(get()) }
            factory { SaveUser(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `on init should be called get user data`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        createViewModel()
        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
        }
    }

    @Test
    fun `on init should be called find adverts by author`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        createViewModel()
        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(values, loading)
            vm.refresh(ProfileFragment.TYPES.ON_SALE)
            assertEquals(values, loading + loading)
        }
    }
}