package es.architectcoders.mascotas.ui.advert

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.architectcoders.domain.Advert
import es.architectcoders.mascotas.ui.advert.viewmodel.AdvertDetailViewModel
import es.architectcoders.usescases.GetAdvert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AdvertDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getAdvert: GetAdvert

    @Mock
    lateinit var loadingObserver: Observer<Boolean>
    @Mock
    lateinit var advertsObserver: Observer<Advert>

    private lateinit var vm: AdvertDetailViewModel
    private val id = "1"

    @Before
    fun setUp() {
        vm = AdvertDetailViewModel(id, getAdvert, Dispatchers.Unconfined)
    }
    @Test
    fun `observing LiveData show loading progress`() {
        vm.loading.observeForever(loadingObserver)
        verify(loadingObserver).onChanged(true)
    }
    @Test
    fun `observing LiveData launches get advert by id`() {
        runBlocking {
            val advert = Advert(id = id)
            whenever(getAdvert.invoke(id)).thenReturn(advert)
            vm.loading.observeForever(loadingObserver)
            vm.advert.observeForever(advertsObserver)
            verify(advertsObserver).onChanged(advert)
            verify(loadingObserver).onChanged(false)
        }
    }
}