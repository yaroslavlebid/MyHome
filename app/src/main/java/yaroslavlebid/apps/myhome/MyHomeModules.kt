package yaroslavlebid.apps.myhome

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import yaroslavlebid.apps.myhome.repository.AuthRepository
import yaroslavlebid.apps.myhome.repository.AuthRepositoryImpl
import yaroslavlebid.apps.myhome.repository.UserRepository
import yaroslavlebid.apps.myhome.repository.UserRepositoryImpl
import yaroslavlebid.apps.myhome.ui.login.sign_up.SignUpViewModel

val viewModelModule = module {
    viewModel { SignUpViewModel(get()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}

val utilsModule = module {

}

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
}