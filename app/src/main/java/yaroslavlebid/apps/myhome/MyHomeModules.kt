package yaroslavlebid.apps.myhome

import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import yaroslavlebid.apps.myhome.repository.AuthRepository
import yaroslavlebid.apps.myhome.repository.AuthRepositoryImpl
import yaroslavlebid.apps.myhome.ui.login.ui.login.sign_up.SignUpViewModel

val viewModelModule = module {
    viewModel { SignUpViewModel(get()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

val utilsModule = module {

}

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
}