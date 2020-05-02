package es.architectcoders.mascotas.ui.advert.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.architectcoders.macotas.sharedtest.utils.loading
import es.architectcoders.mascotas.ui.advert.viewmodel.event.AdvertNavigationEvent
import es.architectcoders.mascotas.utils.*
import es.architectcoders.usescases.FindRelevantAdverts
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
class AdvertListIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    private lateinit var vm: AdvertListViewModel

    private fun createViewModel() {
        val vmModule = module {
            factory { AdvertListViewModel(get(), get()) }
            factory { FindRelevantAdverts(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `on init should be called find relevant adverts`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        createViewModel()
        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(defaultFakeAdverts, vm.adverts.getValueForTest())
            assertEquals(values, loading)
        }
    }

    @Test
    fun `on create advert click should navigate to create advert`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        createViewModel()
        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(defaultFakeAdverts, vm.adverts.getValueForTest())
            assertEquals(values, loading)
            vm.onCreateAdvertClicked()
            assert(vm.nav.getValueForTest()?.peekContent() is AdvertNavigationEvent.CreateAdvertNavigation)
        }
    }

    @Test
    fun `on click advert click should navigate to detail advert`() = coroutinesTestRule.runBlockingTest {
        coroutinesTestRule.pauseDispatcher()
        createViewModel()
        vm.loading.captureValues {
            assertNull(vm.nav.getValueForTest())
            coroutinesTestRule.resumeDispatcher()
            assertEquals(defaultFakeAdverts, vm.adverts.getValueForTest())
            assertEquals(values, loading)
            vm.onAdvertClicked(defaultFakeAdverts[0])
            assert(vm.nav.getValueForTest()?.peekContent() is AdvertNavigationEvent.AdvertDetailNavigation)
            assertEquals(
                (vm.nav.getValueForTest()?.peekContent() as
                        AdvertNavigationEvent.AdvertDetailNavigation).id, defaultFakeAdverts[0].id
            )
        }
    }
}
