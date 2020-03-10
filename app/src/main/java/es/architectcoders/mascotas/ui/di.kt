package es.architectcoders.mascotas.ui

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import es.architectcoders.mascotas.model.LoginRepository
import es.architectcoders.mascotas.ui.common.ResourceProvider
import es.architectcoders.mascotas.ui.login.fragment.LoginFragment
import es.architectcoders.mascotas.ui.login.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}
private val appModule = module {
    factory { FirebaseAuth.getInstance() }
    factory { ResourceProvider(androidContext().resources) }

    single <CoroutineDispatcher> { Dispatchers.Main }
}

val dataModule = module {
    factory { LoginRepository(get()) }
}

private val scopesModule = module {
    scope(named<LoginFragment>()) {
        viewModel { LoginViewModel(get(), get(), get()) }
    }
}