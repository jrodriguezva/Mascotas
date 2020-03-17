package es.architectcoders.mascotas.application

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import es.architectcoders.data.datasource.FirestoreDataSource
import es.architectcoders.data.datasource.LoginDataSource
import es.architectcoders.data.repository.AdvertRepository
import es.architectcoders.data.repository.LoginRepository
import es.architectcoders.mascotas.datasource.FirestoreDataSourceImpl
import es.architectcoders.mascotas.datasource.LoginDataSourceImpl
import es.architectcoders.mascotas.ui.advert.fragment.NewAdvertFragment
import es.architectcoders.mascotas.ui.advert.viewmodel.NewAdvertViewModel
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.login.fragment.LoginFragment
import es.architectcoders.mascotas.ui.login.viewmodel.LoginViewModel
import es.architectcoders.usescases.CreateAdvert
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
    factory { ResourceProvider(androidContext().resources) }
    factory<LoginDataSource> { LoginDataSourceImpl(get()) }
    factory<FirestoreDataSource> { FirestoreDataSourceImpl(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
}

val dataModule = module {
    factory { LoginRepository(get()) }
    factory { AdvertRepository(get()) }
}

private val scopesModule = module {
    scope(named<LoginFragment>()) {
        viewModel { LoginViewModel(get(), get(), get()) }
    }

    scope(named<NewAdvertFragment>()) {
        viewModel { NewAdvertViewModel(get(), get()) }
        scoped { CreateAdvert(get()) }
    }
}