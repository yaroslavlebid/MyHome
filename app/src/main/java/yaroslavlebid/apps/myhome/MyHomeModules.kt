package yaroslavlebid.apps.myhome

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.yanzhenjie.album.api.widget.Widget
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.repository.ApartmentRepository
import yaroslavlebid.apps.myhome.repository.ApartmentRepositoryImpl
import yaroslavlebid.apps.myhome.repository.AuthRepository
import yaroslavlebid.apps.myhome.repository.AuthRepositoryImpl
import yaroslavlebid.apps.myhome.repository.FavoriteRepository
import yaroslavlebid.apps.myhome.repository.FavoriteRepositoryImpl
import yaroslavlebid.apps.myhome.repository.ReviewRepository
import yaroslavlebid.apps.myhome.repository.ReviewRepositoryImpl
import yaroslavlebid.apps.myhome.repository.StorageRepository
import yaroslavlebid.apps.myhome.repository.StorageRepositoryImpl
import yaroslavlebid.apps.myhome.repository.UserRepository
import yaroslavlebid.apps.myhome.repository.UserRepositoryImpl
import yaroslavlebid.apps.myhome.ui.edit_profile.ProfileViewModel
import yaroslavlebid.apps.myhome.ui.home.apartments.ApartmentListViewModel
import yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment.ApartmentViewModel
import yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment.room.RoomListViewModel
import yaroslavlebid.apps.myhome.ui.home.my_profile.MyProfileViewModel
import yaroslavlebid.apps.myhome.ui.home.my_profile.add_apartment.AddApartmentViewModel
import yaroslavlebid.apps.myhome.ui.home.saved_apartments.SavedApartmentsViewModel
import yaroslavlebid.apps.myhome.ui.login.sign_in.SignInViewModel
import yaroslavlebid.apps.myhome.ui.login.sign_up.SignUpViewModel

val viewModelModule = module {
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { SignInViewModel(get())}
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { ApartmentListViewModel(get(), get()) }
    viewModel { MyProfileViewModel(get(), get()) }
    viewModel { SavedApartmentsViewModel(get()) }
    viewModel { ApartmentViewModel(get(), get()) }
    viewModel { AddApartmentViewModel(get(), get()) }
    viewModel { RoomListViewModel(get()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<StorageRepository> { StorageRepositoryImpl(get()) }
    single<ApartmentRepository> { ApartmentRepositoryImpl(get())}
    single<FavoriteRepository> { FavoriteRepositoryImpl(get())}
    single<ReviewRepository> { ReviewRepositoryImpl(get())}
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