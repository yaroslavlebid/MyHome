package yaroslavlebid.apps.myhome.ui.home.my_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.repository.AuthRepository
import yaroslavlebid.apps.myhome.repository.UserRepository
import yaroslavlebid.apps.myhome.utils.setImageFromUrl
import yaroslavlebid.apps.myhome.utils.toUser

class MyProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLogoutSuccess = MutableLiveData<Boolean>()
    val isLogoutSuccess: LiveData<Boolean> = _isLogoutSuccess

    private val _profileImage = MutableLiveData<String>()
    val profileImage: LiveData<String> = _profileImage

    fun doLogout() {
        authRepository.signOut()
        _isLogoutSuccess.value = true
    }

    fun requestProfileImage() {
        val currentUser = authRepository.getCurrentFirebaseUser() ?: return
        userRepository.getUserById(currentUser.uid).addOnSuccessListener {
            _profileImage.value = it.toUser()?.photoUrl
        }.addOnFailureListener {
            Timber.e(it, "Can't retrive user!")
        }
    }
}