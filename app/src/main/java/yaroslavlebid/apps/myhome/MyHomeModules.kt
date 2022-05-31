package yaroslavlebid.apps.myhome

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.api.widget.Widget
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.dsl.single
import yaroslavlebid.apps.myhome.repository.*
import yaroslavlebid.apps.myhome.ui.home.apartments.ApartmentListViewModel
import yaroslavlebid.apps.myhome.ui.login.sign_in.SignInViewModel
import yaroslavlebid.apps.myhome.ui.login.sign_up.SignUpViewModel
import yaroslavlebid.apps.myhome.ui.home.my_profile.MyProfileViewModel
import yaroslavlebid.apps.myhome.ui.edit_profile.ProfileViewModel

val viewModelModule = module {
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { SignInViewModel(get())}
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { ApartmentListViewModel(get()) }
    viewModel { MyProfileViewModel(get()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<StorageRepository> { StorageRepositoryImpl(get()) }
    single<ApartmentRepository> { ApartmentRepositoryImpl(get())}
}

val utilsModule = module {
    single(named("dark_widget")) { Widget.newDarkBuilder(androidContext())
        .title(androidContext().getString(R.string.select_image))
        .statusBarColor(androidContext().getColor(R.color.md_theme_dark_primary))
        .toolBarColor(androidContext().getColor(R.color.md_theme_dark_primary))
        .mediaItemCheckSelector(androidContext().getColor(R.color.md_theme_dark_onPrimary), androidContext().getColor(R.color.md_theme_dark_tertiary))
        .build()
    }

    single(named("light_widget")) { Widget.newLightBuilder(androidContext())
        .title(androidContext().getString(R.string.select_image))
        .statusBarColor(androidContext().getColor(R.color.md_theme_light_primary))
        .toolBarColor(androidContext().getColor(R.color.md_theme_light_primary))
        .mediaItemCheckSelector(androidContext().getColor(R.color.md_theme_light_onPrimary), androidContext().getColor(R.color.md_theme_light_tertiary))
        .build()
    }
}

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }
}