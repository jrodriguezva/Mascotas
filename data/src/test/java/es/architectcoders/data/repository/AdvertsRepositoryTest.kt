package es.architectcoders.data.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.data.repository.AdvertRepository
import es.architectcoders.domain.Advert
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AdvertsRepositoryTest {
    @Mock lateinit var firestoreDataSource: FirestoreDataSource
    private lateinit var advertsRepository: AdvertRepository
    @Before
    fun setUp() {
        advertsRepository = AdvertRepository(firestoreDataSource)
    }

    @Test
    fun `add advert to firebase`() {
        runBlocking {
            val adverts = Advert(id = "1")
            advertsRepository.createAdvert(adverts)
            verify(firestoreDataSource).addAdvert(adverts)
        }
    }
    @Test
    fun `get adverts from firebase`() {
        runBlocking {
            val adverts = listOf(Advert(id = "1"), Advert(id = "2"), Advert(id = "3"))
            whenever(firestoreDataSource.getAdverts()).thenReturn(adverts)
            val result = advertsRepository.findRelevantAdverts()
            assertEquals(adverts, result)
        }
    }

    @Test
    fun `find advert by Id from firebase`() {
        runBlocking {
            val adverts = Advert(id = "1")
            whenever(firestoreDataSource.getAdvert(any())).thenReturn(adverts)
            val result = advertsRepository.getAdvert("1")
            assertEquals(adverts, result)
        }
    }

    @Test
    fun `find advert by Author Id from firebase`() {
        runBlocking {
            val adverts = listOf(Advert(id = "1"), Advert(id = "2"), Advert(id = "3"))
            whenever(firestoreDataSource.getAdvertsByAuthor(any())).thenReturn(adverts)
            val result = advertsRepository.findAdvertsByAuthor("1")
            assertEquals(adverts, result)
        }
    }
}