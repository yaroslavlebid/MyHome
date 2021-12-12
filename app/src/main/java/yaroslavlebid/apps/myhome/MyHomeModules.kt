package yaroslavlebid.apps.myhome

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import yaroslavlebid.apps.myhome.repository.*
import yaroslavlebid.apps.myhome.ui.apartments.ApartmentListViewModel
import yaroslavlebid.apps.myhome.ui.login.sign_in.SignInViewModel
import yaroslavlebid.apps.myhome.ui.login.sign_up.SignUpViewModel
import yaroslavlebid.apps.myhome.ui.profile.ProfileViewModel

val viewModelModule = module {
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { SignInViewModel(get())}
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { ApartmentListViewModel(get()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<StorageRepository> { StorageRepositoryImpl(get()) }
    single<ApartmentRepository> { ApartmentRepositoryImpl(get())}
}

val utilsModule = module {

}

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
}