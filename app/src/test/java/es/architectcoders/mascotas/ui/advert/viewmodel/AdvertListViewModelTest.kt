package es.architectcoders.mascotas.ui.advert.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.architectcoders.domain.Advert
import es.architectcoders.usescases.FindRelevantAdverts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AdvertListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findRelevantAdverts: FindRelevantAdverts

    @Mock
    lateinit var loadingObserver: Observer<Boolean>
    @Mock
    lateinit var advertsObserver: Observer<List<Advert>>

    private lateinit var vm: AdvertListViewModel

    @Before
    fun setUp() {
        vm = AdvertListViewModel(findRelevantAdverts, Dispatchers.Unconfined)
    }
    @Test
    fun `observing LiveData launches find relevant adverts`() {
        runBlocking {
            val adverts = listOf(Advert(id = "1"), Advert(id = "2"))
            whenever(findRelevantAdverts.invoke()).thenReturn(adverts)
            vm.loading.observeForever(loadingObserver)
            vm.adverts.observeForever(advertsObserver)
            verify(loadingObserver).onChanged(true)
            verify(advertsObserver).onChanged(adverts)
            verify(loadingObserver).onChanged(false)
        }
    }
}