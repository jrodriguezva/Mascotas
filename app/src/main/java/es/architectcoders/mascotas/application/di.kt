package es.architectcoders.mascotas.application

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.data.datasource.LoginDataSource
import es.architectcoders.data.datasource.UserDataSource
import es.architectcoders.data.repository.AdvertRepository
import es.architectcoders.data.repository.LoginRepository
import es.architectcoders.data.repository.UserRepository
import es.architectcoders.mascotas.datasource.FirestoreDataSourceImpl
import es.architectcoders.mascotas.datasource.LoginDataSourceImpl
import es.architectcoders.mascotas.datasource.UserDataSourceImpl
import es.architectcoders.mascotas.ui.advert.fragment.AdvertDetailFragment
import es.architectcoders.mascotas.ui.advert.fragment.AdvertListFragment
import es.architectcoders.mascotas.ui.advert.fragment.NewAdvertFragment
import es.architectcoders.mascotas.ui.advert.viewmodel.AdvertDetailViewModel
import es.architectcoders.mascotas.ui.advert.viewmodel.AdvertListViewModel
import es.architectcoders.mascotas.ui.advert.viewmodel.NewAdvertViewModel
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.common.ResourceProviderImpl
import es.architectcoders.mascotas.ui.common.ValidatorUtil
import es.architectcoders.mascotas.ui.common.ValidatorUtilImpl
import es.architectcoders.mascotas.ui.login.fragment.LoginFragment
import es.architectcoders.mascotas.ui.login.viewmodel.LoginViewModel
import es.architectcoders.mascotas.ui.profile.fragments.EditProfileFragment
import es.architectcoders.mascotas.ui.profile.fragments.ProfileFragment
import es.architectcoders.mascotas.ui.profile.viewmodel.EditProfileViewModel
import es.architectcoders.mascotas.ui.profile.viewmodel.ProfileViewModel
import es.architectcoders.usescases.*
import es.architectcoders.usescases.account.CreateAccountInteractor
import es.architectcoders.usescases.login.GetCurrentUserInteractor
import es.architectcoders.usescases.login.SignInInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(
            listOf(
                appModule,
                dataModule,
                scopesModule
            )
        )
    }
}

private val appModule = module {
    factory { FirebaseAuth.getInstance() }
    factory { Firebase.firestore }
    factory<ResourceProvider> { ResourceProviderImpl(androidContext().resources) }
    factory<LoginDataSource> { LoginDataSourceImpl(get()) }
    factory<FirestoreDataSource> { FirestoreDataSourceImpl(get()) }
    factory<UserDataSource> { UserDataSourceImpl(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single<ValidatorUtil> { ValidatorUtilImpl() }
}

val dataModule = module {
    factory { LoginRepository(get()) }
    factory { AdvertRepository(get()) }
    factory { UserRepository(get()) }
}

private val scopesModule = module {
    scope(named<LoginFragment>()) {
        viewModel { LoginViewModel(get(), get(), get(), get(), get()) }
        scoped { GetCurrentUserInteractor(get()) }
        scoped { SignInInteractor(get()) }
        scoped { CreateAccountInteractor(get()) }
    }

    scope(named<NewAdvertFragment>()) {
        viewModel { NewAdvertViewModel(get(), get(), get()) }
        scoped { CreateAdvert(get()) }
    }

    scope(named<AdvertDetailFragment>()) {
        viewModel { (id: String) -> AdvertDetailViewModel(id, get(), get()) }
        scoped { GetAdvert(get()) }
    }

    scope(named<AdvertListFragment>()) {
        viewModel { AdvertListViewModel(get(), get()) }
        scoped { FindRelevantAdverts(get()) }
    }

    scope(named<ProfileFragment>()) {
        viewModel { ProfileViewModel(get(), get(), get(), get(), get(), get()) }
        scoped { FindAdvertsByAuthor(get()) }
        scoped { GetUser(get()) }
        scoped { SaveUser(get()) }
    }

    scope(named<EditProfileFragment>()) {
        viewModel { EditProfileViewModel(get(), get(), get(), get(), get()) }
        scoped { GetUser(get()) }
        scoped { SaveUser(get()) }
    }
}