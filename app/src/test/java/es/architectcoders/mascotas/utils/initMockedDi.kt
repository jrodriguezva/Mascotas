package es.architectcoders.mascotas.utils

import arrow.core.Either
import arrow.core.Right
import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.data.datasource.LoginDataSource
import es.architectcoders.data.datasource.UserDataSource
import es.architectcoders.data.repository.ErrorLoginRepository
import es.architectcoders.data.repository.RepositoryException
import es.architectcoders.domain.Advert
import es.architectcoders.domain.User
import es.architectcoders.macotas.sharedtest.utils.mockAdvert
import es.architectcoders.macotas.sharedtest.utils.mockUser
import es.architectcoders.mascotas.application.dataModule
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.common.ValidatorUtil
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single<LoginDataSource> { FakeLocalDataSource() }
    single<FirestoreDataSource> { FakeRemoteDataSource() }
    single<UserDataSource> { FakeRemoteUserDataSource() }
    single<ValidatorUtil> { FakeValidatorUtil() }
    single<ResourceProvider> { FakeResourceProvider() }
    single { Dispatchers.Unconfined }
}

val defaultFakeAdverts = listOf(
    mockAdvert.copy("1"),
    mockAdvert.copy("2"),
    mockAdvert.copy("3"),
    mockAdvert.copy("4")
)

val defaultFakeUsers = listOf(
    mockUser.copy("1a@a.com"),
    mockUser.copy("2a@a.com"),
    mockUser.copy("3a@a.com"),
    mockUser.copy("4a@a.com")
)

class FakeResourceProvider : ResourceProvider {
    override fun getString(stringResId: Int) = "$stringResId"
}

class FakeValidatorUtil : ValidatorUtil {
    override fun validateEmail(email: String) = null

    override fun validatePassword(password: String) = null

    override fun validateName(name: String) = null

    override fun validateSurname(surname: String) = null

    override fun validateCity(city: String) = null

    override fun validateCountry(country: String) = null
}

class FakeLocalDataSource : LoginDataSource {
    private val user: User = User("a@a.com", "a", "a", "", 5, 1, "")

    override suspend fun signIn(email: String, password: String): Either<ErrorLoginRepository, Boolean> = Right(true)

    override suspend fun createAccount(email: String, password: String) = Right(true)

    override suspend fun getCurrentUser(): Either<ErrorLoginRepository, User> = Right(user)
}

class FakeRemoteDataSource : FirestoreDataSource {

    override suspend fun addAdvert(advert: Advert) = Right(mockAdvert)

    override suspend fun getAdverts() = defaultFakeAdverts

    override suspend fun getAdvert(id: String) = defaultFakeAdverts.first { it.id == id }

    override suspend fun getAdvertsByAuthor(author: String) = defaultFakeAdverts.filter { it.author == author }
}
 class FakeRemoteUserDataSource : UserDataSource {

     override suspend fun getUser(email: String) = defaultFakeUsers.first { it.email == email }

     override suspend fun saveUser(user: User): Either<RepositoryException, User> {
         defaultFakeUsers + user
         return Right(user)
     }

 }