package yaroslavlebid.apps.myhome.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.data.User
import yaroslavlebid.apps.myhome.repository.AuthRepository
import yaroslavlebid.apps.myhome.repository.UserRepository
import yaroslavlebid.apps.myhome.ui.login.sign_up.RegistrationEvent
import yaroslavlebid.apps.myhome.ui.login.sign_up.RegistrationStatus
import yaroslavlebid.apps.myhome.ui.login.sign_up.RegistrationStatusMap

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _canConfirmProfile = MutableLiveData<Boolean>()
    val canConfirmProfile: LiveData<Boolean> = _canConfirmProfile



    fun setUpProfile(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUrl: String,
        dateOfBirth: String
    ) {
        val firebaseCurrentUser = authRepository.getCurrentFirebaseUser()
        if (firebaseCurrentUser != null) {
            _isLoading.value = true
            userRepository.getUserById(firebaseCurrentUser.uid)
                .addOnSuccessListener { dataSnapshot ->
                    var user = dataSnapshot.toObject(User::class.java)
                    if (user == null) {
                        /*_fillUserProfileStatus.value = RegistrationEvent(
                            RegistrationStatus.ERROR_DEFAULT,
                            RegistrationStatusMap.getErrorMessage(RegistrationStatus.ERROR_DEFAULT)
                        )*/
                            _canConfirmProfile.value = false
                        return@addOnSuccessListener
                    } else {
                        user.lastName = lastName
                        user.dateOfBirth = dateOfBirth
                        user.firstName = firstName
                        user.phoneNumber = phoneNumber
                        user.photoUrl = photoUrl
                        userRepository.updateUser(user).addOnSuccessListener {
                            Timber.d("User ${user.id} updated successful!")
                            /*_fillUserProfileStatus.value =
                                RegistrationEvent(RegistrationStatus.SUCCESS)*/
                            _canConfirmProfile.value = true
                        }.addOnFailureListener {
                            Timber.e("Error, can't update user profile")
                            _canConfirmProfile.value = false
                        }
                    }
                }.addOnFailureListener {
                    Timber.e("Error, when try to get user", it)
                    _canConfirmProfile.value = false
                }.addOnCompleteListener {
                    _isLoading.value = false
                }
        }

    }
}