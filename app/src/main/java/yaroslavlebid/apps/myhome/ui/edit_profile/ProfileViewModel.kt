package yaroslavlebid.apps.myhome.ui.edit_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.data.User
import yaroslavlebid.apps.myhome.repository.AuthRepository
import yaroslavlebid.apps.myhome.repository.StorageRepository
import yaroslavlebid.apps.myhome.repository.UserRepository
import java.io.File
import yaroslavlebid.apps.myhome.utils.toUser

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val storageRepository: StorageRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isProfileConfirmed = MutableLiveData<Boolean>();
    val isProfileConfirmed: LiveData<Boolean> = _isProfileConfirmed

    private val _canConfrimProfile = MutableLiveData<Boolean>();
    val canConfirmProfile: LiveData<Boolean> = _canConfrimProfile

    private val _isProfileImageLoaded = MutableLiveData<Boolean>()
    val isProfileImageLoaded: LiveData<Boolean> = _isProfileImageLoaded

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _userLiveData

    var localUserProfile = User()

    fun initUser() {
        val firebaseCurrentUser = authRepository.getCurrentFirebaseUser()
        if (firebaseCurrentUser != null) {
            _isLoading.value = true
            userRepository.getUserById(firebaseCurrentUser.uid)
                .addOnSuccessListener { dataSnapshot ->
                    var user = dataSnapshot.toUser()
                    if (user == null) {
                        Timber.e("Cannot fetch user from db, error!")
                        return@addOnSuccessListener
                    } else {
                        _userLiveData.value = user.copy()
                        localUserProfile = user
                    }
                }.addOnFailureListener {
                    Timber.e("Error, when try to get user", it)
                }.addOnCompleteListener {
                    _isLoading.value = false
                }
        }
    }

    fun updateUser(email:String = "",
                   firstName: String = "",
                   lastName: String = "",
                   phoneNumber: String = "",
                   photoUrl: String = "") {
        if (email.isNotEmpty()) localUserProfile.email = email
        if (firstName.isNotEmpty()) localUserProfile.firstName = firstName
        if (lastName.isNotEmpty()) localUserProfile.lastName = lastName
        if (phoneNumber.isNotEmpty()) localUserProfile.phoneNumber = phoneNumber
        if (photoUrl.isNotEmpty()) localUserProfile.photoUrl = photoUrl
    }

    fun loadProfileImage(path: String) {
        _isLoading.value = true
        storageRepository.uploadProfileImage(File(path))
            .addOnCompleteListener {
                _isLoading.value = false
            }
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {
                    updateUser(photoUrl = it.toString())
                }
                _isProfileImageLoaded.value = true
            }
            .addOnFailureListener {
                _isProfileImageLoaded.value = false
            }
    }

    fun confirmProfile() {
        _isLoading.value = true
        userRepository.updateUser(localUserProfile).addOnSuccessListener {
            Timber.d("User ${localUserProfile.id} updated successful!")
            _isProfileConfirmed.value = true
        }.addOnFailureListener {
            Timber.e("Error, can't update user profile")
            _isProfileConfirmed.value = false
        }.addOnCompleteListener {
            _isLoading.value = false
        }
    }

    fun requestCanConfirmProfile(name: String, surname: String, number: String, isPrivacyPolicyConfirmed: Boolean) {
        if (name.isNotEmpty() && surname.isNotEmpty() && number.isNotEmpty() && isPrivacyPolicyConfirmed)
            _canConfrimProfile.value = true
        else _canConfrimProfile.value = false
    }
}